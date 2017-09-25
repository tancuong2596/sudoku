/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ctu.cit.sudoku.Views;

import edu.ctu.cit.sudoku.Commands.Command;
import edu.ctu.cit.sudoku.Commands.SetCellNumberCommand;
import edu.ctu.cit.sudoku.Models.Cell;
import edu.ctu.cit.sudoku.Models.Puzzle;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Consumer;
import javax.swing.JDialog;

/**
 *
 * @author charlie
 */
public class PuzzleBoard extends javax.swing.JPanel {

    class Remote {

        private Stack<Command> undoCommands = null;
        private Stack<Command> redoCommands = null;

        public Remote() {
            this.undoCommands = new Stack<>();
            this.redoCommands = new Stack<>();
        }

        public void change(int x, int y, int newValue) {
            Command command = new SetCellNumberCommand(puzzleUserAnswer, grid[x][y], x, y, newValue);
            this.undoCommands.add(command);
            redoCommands.clear();
            command.execute();
        }

        public PuzzleCell undo() {
            if (!undoCommands.isEmpty()) {
                SetCellNumberCommand command = (SetCellNumberCommand) this.undoCommands.pop();
                command.undo();
                this.redoCommands.add(command);
                return command.getCell();
            }
            return null;
        }

        public PuzzleCell redo() {
            if (!redoCommands.isEmpty()) {
                SetCellNumberCommand command = (SetCellNumberCommand) this.redoCommands.pop();
                command.execute();
                this.undoCommands.add(command);
                return command.getCell();
            }
            return null;
        }
    }

    private NumberChooser numberChooser;
    private PuzzleCell[][] grid = new PuzzleCell[Puzzle.BOARD_SIZE][Puzzle.BOARD_SIZE];
    private PuzzleCell selectedPuzzleCell = null;
    private int selectedPuzzleCellX;
    private int selectedPuzzleCellY;
    private Puzzle puzzle = null;
    private Puzzle puzzleUserAnswer = null;
    private boolean isRepeatedCellCheck = false;
    private Remote remote = null;

    private final Consumer<Cell> markRepeatedCell = (Cell c) -> {
        PuzzleCell repeatedCell = grid[c.getX()][c.getY()];
        repeatedCell.setRepeated(true);
    };

    /**
     * Creates new form PuzzleBoard
     */
    public PuzzleBoard(Component parent) {
        initVariables();
        if (parent instanceof Frame) {
            numberChooser = new NumberChooser((Frame) parent);
        } else {
            numberChooser = new NumberChooser((JDialog) parent);
        }
        initComponents();
        initPuzzleCells();
        numberChooser.setNumberSelected((int number) -> {
            if (PuzzleBoard.this.selectedPuzzleCell != null) {
                PuzzleBoard.this.remote.change(selectedPuzzleCellX, selectedPuzzleCellY, number);
            }
        });
    }

    private void initVariables() {
        selectedPuzzleCell = null;
        selectedPuzzleCellX = -1;
        selectedPuzzleCellY = -1;
        remote = new Remote();
        puzzleUserAnswer = null;
        puzzle = null;
    }

    public boolean isEdited() {
        for (int i = 0; i < Puzzle.BOARD_SIZE; i++) {
            for (int j = 0; j < Puzzle.BOARD_SIZE; j++) {
                if (this.puzzle.get(i, j) != this.puzzleUserAnswer.get(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initPuzzleCells() {
        GridLayout layout = new GridLayout(Puzzle.BOARD_SIZE, Puzzle.BOARD_SIZE);
        this.setLayout(layout);

        for (int i = 0; i < Puzzle.BOARD_SIZE; i++) {
            for (int j = 0; j < Puzzle.BOARD_SIZE; j++) {
                grid[i][j] = new PuzzleCell();

                final PuzzleCell finalSelectedCell = grid[i][j];
                final int finalI = i;
                final int finalJ = j;

                grid[i][j].setOnPuzzleCellClicked((PuzzleCell cell) -> {
                    if (selectedPuzzleCell != null) {
                        selectedPuzzleCell.setState(PuzzleCell.STATE_ENABLE);
                    }
                    finalSelectedCell.setState(PuzzleCell.STATE_SELECTED);

                    PuzzleBoard.this.numberChooser.setLocation(cell.getCurrentLocation());
                    if (PuzzleBoard.this.numberChooser.isVisible() && selectedPuzzleCell == finalSelectedCell) {
                        PuzzleBoard.this.numberChooser.close();
                    } else {
                        PuzzleBoard.this.numberChooser.setVisible(true);
                    }

                    selectedPuzzleCell = finalSelectedCell;
                    selectedPuzzleCellX = finalI;
                    selectedPuzzleCellY = finalJ;
                });

                grid[i][j].setOnPuzzleCellValueChanged((PuzzleCell cell, int oldValue, int newValue) -> {
                    checkRepeatedCells();
                });

                this.add(grid[i][j]);
            }
        }
    }

    public void undo() {
        if (this.selectedPuzzleCell != null) {
            this.selectedPuzzleCell.setState(PuzzleCell.STATE_ENABLE);
        }

        PuzzleCell cell = this.remote.undo();

        if (cell != null) {
            this.selectedPuzzleCell = cell;
            this.selectedPuzzleCell.setState(PuzzleCell.STATE_SELECTED);
        }
    }

    public void redo() {
        if (this.selectedPuzzleCell != null) {
            this.selectedPuzzleCell.setState(PuzzleCell.STATE_ENABLE);
        }

        PuzzleCell cell = this.remote.redo();

        if (cell != null) {
            this.selectedPuzzleCell = cell;
            this.selectedPuzzleCell.setState(PuzzleCell.STATE_SELECTED);
        }
    }

    public void checkRepeatedCells() {
        if (PuzzleBoard.this.isRepeatedCellCheck) {
            clearMistakes();
            for (int i = 0; i < Puzzle.BOARD_SIZE; i++) {
                puzzleUserAnswer
                        .checkRow(i)
                        .forEach(markRepeatedCell);
                puzzleUserAnswer
                        .checkColumn(i)
                        .forEach(markRepeatedCell);
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    puzzleUserAnswer
                            .checkGroup(i, j)
                            .forEach(markRepeatedCell);
                }
            }
        }
    }

    public void closeNumberChooser() {
        if (this.numberChooser != null) {
            this.numberChooser.close();
        }
    }

    public Puzzle getCurrentPuzzleConfiguration() {
        Puzzle currPuz = new Puzzle();
        for (int i = 0; i < Puzzle.BOARD_SIZE; i++) {
            for (int j = 0; j < Puzzle.BOARD_SIZE; j++) {
                currPuz.set(i, j, this.grid[i][j].getValue());
            }
        }
        return currPuz;
    }

    public void clearMistakes() {
        for (int i = 0; i < Puzzle.BOARD_SIZE; i++) {
            for (int j = 0; j < Puzzle.BOARD_SIZE; j++) {
                if (grid[i][j].isRepeated()) {
                    grid[i][j].setRepeated(false);
                }
            }
        }
    }

    public void setPuzzle(Puzzle puzzle) {
        initVariables();
        this.puzzle = puzzle;
        this.puzzleUserAnswer = new Puzzle(puzzle);
        clearMistakes();
        for (int i = 0; i < Puzzle.BOARD_SIZE; i++) {
            for (int j = 0; j < Puzzle.BOARD_SIZE; j++) {
                grid[i][j].reset();
                if (puzzle.get(i, j) != 0) {
                    grid[i][j].setText("" + puzzle.get(i, j));
                    grid[i][j].setState(PuzzleCell.STATE_DISABLE);
                } else {
                    grid[i][j].setState(PuzzleCell.STATE_ENABLE);
                }
            }
        }
    }

    public boolean isRepeatedCellCheck() {
        return isRepeatedCellCheck;
    }

    public void setRepeatedCellCheck(boolean isRepeatedCellCheck) {
        this.isRepeatedCellCheck = isRepeatedCellCheck;
        if (isRepeatedCellCheck) {
            checkRepeatedCells();
        } else {
            clearMistakes();
        }
    }

    public Puzzle getPuzzleUserAnswer() {
        return puzzleUserAnswer;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

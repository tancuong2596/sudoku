/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ctu.cit.sudoku.Views;

import edu.ctu.cit.sudoku.Controllers.PuzzleBoardController;
import edu.ctu.cit.sudoku.Controllers.StatusController;
import edu.ctu.cit.sudoku.Databases.HighScoreDbHelper;
import edu.ctu.cit.sudoku.Factories.PuzzleFactory;
import edu.ctu.cit.sudoku.Models.Puzzle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author charlie
 */
public class MainWindow extends javax.swing.JFrame implements ActionListener {

    private static final int TICK_COUNT_LIMIT = 99 * 60 + 59;
    private static final int GAME_RESULT_TIME_UP = 0;
    private static final int GAME_RESULT_USER_SOLVE_PUZZLE = 1;
    private static final int GAME_RESULT_USER_GIVE_UP = 2;

    public static class TimeLimitExceededException extends Exception {

        private TimeLimitExceededException(String times_up_Game_over) {
            super(times_up_Game_over);
        }
    }

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        statusController = new StatusController(this.labelStatus);
        getContentPane().add(this.puzzleBoardController.getPuzzleBoard(), java.awt.BorderLayout.CENTER);
        this.menuHintForRepeatedNumbers.setSelected(true);
        this.puzzleBoardController.setRepeatedCellCheck(true);
        this.puzzleBoardController.setUseWonTheGameHandler(this.onUserWonTheGame);
        try {
            this.dbHelper = new HighScoreDbHelper();
            this.dbHelper.open();
            this.dbHelper.createTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        this.makeMainWindowCenterScreen();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        upperPanel = new javax.swing.JPanel();
        labelTime = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();
        mainMenu = new javax.swing.JMenuBar();
        menuGame = new javax.swing.JMenu();
        menuNewGame = new javax.swing.JMenuItem();
        menuManuallyNumbersInput = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuSavePuzzle = new javax.swing.JMenuItem();
        menuLoadPuzzle = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuPause = new javax.swing.JCheckBoxMenuItem();
        menuHintForRepeatedNumbers = new javax.swing.JCheckBoxMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuUndo = new javax.swing.JMenuItem();
        menuRedo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuGiveUp = new javax.swing.JMenuItem();
        menuClearPuzzle = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuHighScore = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menuAbout = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setForeground(java.awt.Color.gray);
        setPreferredSize(new java.awt.Dimension(384, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        upperPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        upperPanel.setPreferredSize(new java.awt.Dimension(384, 50));
        upperPanel.setLayout(new java.awt.BorderLayout());

        labelTime.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        labelTime.setForeground(new java.awt.Color(0, 0, 0));
        labelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTime.setText("00:00");
        upperPanel.add(labelTime, java.awt.BorderLayout.CENTER);

        getContentPane().add(upperPanel, java.awt.BorderLayout.PAGE_START);

        labelStatus.setText("Ready");
        labelStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        labelStatus.setFocusable(false);
        getContentPane().add(labelStatus, java.awt.BorderLayout.PAGE_END);

        menuGame.setText("Game");

        menuNewGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        menuNewGame.setText("New game...");
        menuNewGame.setName("menuNewGame"); // NOI18N
        menuNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewGameActionPerformed(evt);
            }
        });
        menuGame.add(menuNewGame);

        menuManuallyNumbersInput.setText("Manually numbers input");
        menuManuallyNumbersInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuManuallyNumbersInputActionPerformed(evt);
            }
        });
        menuGame.add(menuManuallyNumbersInput);
        menuGame.add(jSeparator1);

        menuSavePuzzle.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSavePuzzle.setText("Save puzzle...");
        menuSavePuzzle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSavePuzzleActionPerformed(evt);
            }
        });
        menuGame.add(menuSavePuzzle);

        menuLoadPuzzle.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuLoadPuzzle.setText("Load puzzle...");
        menuLoadPuzzle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLoadPuzzleActionPerformed(evt);
            }
        });
        menuGame.add(menuLoadPuzzle);
        menuGame.add(jSeparator5);

        menuPause.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, 0));
        menuPause.setText("Pause");
        menuPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPauseActionPerformed(evt);
            }
        });
        menuGame.add(menuPause);

        menuHintForRepeatedNumbers.setText("Hint for repeated numbers");
        menuHintForRepeatedNumbers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHintForRepeatedNumbersActionPerformed(evt);
            }
        });
        menuGame.add(menuHintForRepeatedNumbers);
        menuGame.add(jSeparator4);

        menuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        menuUndo.setText("Undo");
        menuUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUndoActionPerformed(evt);
            }
        });
        menuGame.add(menuUndo);

        menuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuRedo.setText("Redo");
        menuRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRedoActionPerformed(evt);
            }
        });
        menuGame.add(menuRedo);
        menuGame.add(jSeparator3);

        menuGiveUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SPACE, java.awt.event.InputEvent.CTRL_MASK));
        menuGiveUp.setText("Give up");
        menuGiveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGiveUpActionPerformed(evt);
            }
        });
        menuGame.add(menuGiveUp);

        menuClearPuzzle.setText("Clear puzzle");
        menuClearPuzzle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClearPuzzleActionPerformed(evt);
            }
        });
        menuGame.add(menuClearPuzzle);
        menuGame.add(jSeparator2);

        menuHighScore.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menuHighScore.setText("High score");
        menuHighScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHighScoreActionPerformed(evt);
            }
        });
        menuGame.add(menuHighScore);
        menuGame.add(jSeparator7);

        menuAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuAbout.setText("About");
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });
        menuGame.add(menuAbout);
        menuGame.add(jSeparator6);

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        menuGame.add(menuExit);

        mainMenu.add(menuGame);

        setJMenuBar(mainMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void makeMainWindowCenterScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    private void setTickCount(int tickCount) throws TimeLimitExceededException {
        if (tickCount > TICK_COUNT_LIMIT) {
            throw new TimeLimitExceededException("Time's up. Game over.");
        }

        this.labelTime.setText(String.format("%02d:%02d", tickCount / 60, tickCount % 60));
        this.tickCount = tickCount;
    }

    private void menuNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewGameActionPerformed
        this.pauseGame();
        NewGameDialog newGameDialog = new NewGameDialog(this, true)
                .setOnGameDifficultiesSelected((PuzzleFactory.GameDifficulties difficulties) -> {
                    MainWindow.this.previousGameDifficulties = difficulties;
                    MainWindow.this.newGame();
                });
        newGameDialog.focusOnDifficulty(this.previousGameDifficulties);
        newGameDialog.setLocationRelativeTo(this);
        newGameDialog.setVisible(true);
        this.resumeGame();

    }//GEN-LAST:event_menuNewGameActionPerformed

    private void menuLoadPuzzleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLoadPuzzleActionPerformed
        this.pauseGame();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(this.textFileFilter);
        int retVal = fileChooser.showOpenDialog(this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File file = this.fileChooser.getSelectedFile();
            try {
                if (file != null) {
                    puzzleBoardController.fromFile(file);
                    this.resetTimer();
                    this.timer.stop();
                    JOptionPane.showMessageDialog(this, String.format("Loaded from %s", file.getAbsoluteFile()));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("'%s' is not valid puzzle file", file == null ? "" : file.getAbsoluteFile()),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        this.resumeGame();
    }//GEN-LAST:event_menuLoadPuzzleActionPerformed

    private void menuSavePuzzleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSavePuzzleActionPerformed
        this.pauseGame();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(this.textFileFilter);
        int retVal = fileChooser.showSaveDialog(this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File file = this.fileChooser.getSelectedFile();
            try {
                if (file != null) {
                    puzzleBoardController.toFile(file);
                    JOptionPane.showMessageDialog(this, String.format("Saved to '%s'", file.getAbsoluteFile()));
                    this.timer.stop();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cannot save to this puzzle to '" + (file == null ? "" : file.getAbsolutePath()) + "'",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        this.resumeGame();
    }//GEN-LAST:event_menuSavePuzzleActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        newGame();
    }//GEN-LAST:event_formWindowOpened

    private void menuHintForRepeatedNumbersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHintForRepeatedNumbersActionPerformed
        this.puzzleBoardController.setRepeatedCellCheck(menuHintForRepeatedNumbers.isSelected());
    }//GEN-LAST:event_menuHintForRepeatedNumbersActionPerformed

    private void menuPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPauseActionPerformed
        if (!this.isGameOver) {
            if (this.timer.isRunning()) {
                this.pauseGame();
            } else {
                this.resumeGame();
            }
        }
    }//GEN-LAST:event_menuPauseActionPerformed

    private void menuManuallyNumbersInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuManuallyNumbersInputActionPerformed
        this.pauseGame();
        ManuallyNewGameDialog dialog = new ManuallyNewGameDialog(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setOnUserPressOk(puzzle -> {
            System.out.println("isvalid = " + puzzle.isValidPuzzle());
            if (puzzle.isValidPuzzle()) {
                MainWindow.this.puzzleBoardController.setPuzzle(puzzle);
                MainWindow.this.resetTimer();
            } else {
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Puzzle cannot contain cell with repeated numbers in the same line, row or group."
                //+ "\nAlso, it must contain exactly " + Puzzle.N_BOARD_PRESET_CELLS + " numbers"
                );
            }
            MainWindow.this.resumeGame();
        });
        dialog.setOnUserPressCancel(() -> {
            MainWindow.this.resumeGame();
        });
        dialog.setVisible(true);
    }//GEN-LAST:event_menuManuallyNumbersInputActionPerformed

    private void menuUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUndoActionPerformed
        if (!this.isGameOver) {
            this.puzzleBoardController.undo();
        }
    }//GEN-LAST:event_menuUndoActionPerformed

    private void menuRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRedoActionPerformed
        if (!this.isGameOver) {
            this.puzzleBoardController.redo();
        }
    }//GEN-LAST:event_menuRedoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (this.dbHelper != null) {
            try {
                this.dbHelper.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void menuGiveUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGiveUpActionPerformed
        this.handleGameOver(MainWindow.GAME_RESULT_USER_GIVE_UP);
    }//GEN-LAST:event_menuGiveUpActionPerformed

    private void menuHighScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHighScoreActionPerformed
        this.pauseGame();
        this.showHighScore(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                MainWindow.this.resumeGame();
            }
        });
    }//GEN-LAST:event_menuHighScoreActionPerformed

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        JOptionPane.showMessageDialog(this, "Sudoku version 1.0\nCopyright (c) 2017", "", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_menuAboutActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        this.pauseGame();
        final int dialogResult = JOptionPane.showConfirmDialog(
                this,
                "Do you want to quit sudoku?",
                "Quit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (dialogResult == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            this.resumeGame();
        }
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuClearPuzzleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClearPuzzleActionPerformed
        if (!this.isGameOver) {
            puzzleBoardController.clearPuzzle();
        }
    }//GEN-LAST:event_menuClearPuzzleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelTime;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JMenuItem menuAbout;
    private javax.swing.JMenuItem menuClearPuzzle;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuGame;
    private javax.swing.JMenuItem menuGiveUp;
    private javax.swing.JMenuItem menuHighScore;
    private javax.swing.JCheckBoxMenuItem menuHintForRepeatedNumbers;
    private javax.swing.JMenuItem menuLoadPuzzle;
    private javax.swing.JMenuItem menuManuallyNumbersInput;
    private javax.swing.JMenuItem menuNewGame;
    private javax.swing.JCheckBoxMenuItem menuPause;
    private javax.swing.JMenuItem menuRedo;
    private javax.swing.JMenuItem menuSavePuzzle;
    private javax.swing.JMenuItem menuUndo;
    private javax.swing.JPanel upperPanel;
    // End of variables declaration//GEN-END:variables

    private final PuzzleBoardController puzzleBoardController = new PuzzleBoardController(this);
    private PuzzleFactory.GameDifficulties previousGameDifficulties = PuzzleFactory.GameDifficulties.EXTREMELY_EASY;
    private StatusController statusController = null;
    private Timer timer = new Timer(1000, (ActionListener) this);
    private int tickCount;
    private final JFileChooser fileChooser = new JFileChooser();
    private final FileFilter textFileFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            Path path = FileSystems.getDefault().getPath(f.getParent(), f.getName());
            String mimeType = "";
            try {
                mimeType = Files.probeContentType(path);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            return mimeType.toLowerCase().startsWith("text") || mimeType.toLowerCase().contains("directory");
        }

        @Override
        public String getDescription() {
            return "Text files";
        }
    };
    private String oldUserName = "";
    private HighScoreDbHelper dbHelper = null;
    private final PuzzleBoard.OnUserWonTheGame onUserWonTheGame = () -> {
        this.handleGameOver(MainWindow.GAME_RESULT_USER_SOLVE_PUZZLE);
    };
    private boolean isGameOver = false;

    private void newGame() {
        this.puzzleBoardController.newPuzzleBoard(this.previousGameDifficulties);
        this.resetTimer();
    }

    private void resetTimer() {
        this.menuManuallyNumbersInput.setSelected(false);
        this.isGameOver = false;
        try {
            this.setTickCount(0);
        } catch (TimeLimitExceededException ex) {
            ex.printStackTrace();
        }
        this.timer.restart();
        this.statusController.showMessage("Ready");
        this.resumeGame();
        this.puzzleBoardController.setMenuUndo(this.menuUndo);
        this.puzzleBoardController.setMenuRedo(this.menuRedo);
    }

    private void showHighScore(WindowAdapter wa) {
        this.pauseGame();
        HighScore highScore = new HighScore(this, true, this.dbHelper, wa);
        highScore.setVisible(true);
    }

    private void pauseGame() {
        this.timer.stop();
        this.puzzleBoardController.closeNumberChooser();
        if (!this.isGameOver) {
            this.statusController.showMessage("You couldn't see the puzzle now", StatusController.STATUS_WARNING);
            this.labelTime.setForeground(Color.red);
            this.labelTime.setText("Paused");
            this.puzzleBoardController.hidePuzzleBoard();
            this.menuPause.setSelected(true);
        } else {
            this.statusController.showMessage("The game has been over", StatusController.STATUS_ERROR);
        }
    }

    private void resumeGame() {
        this.puzzleBoardController.closeNumberChooser();
        if (!this.isGameOver) {
            this.timer.start();
            this.statusController.showMessage("Ready");
            this.labelTime.setForeground(Color.black);
            this.labelTime.setText(String.format("%02d:%02d", tickCount / 60, tickCount % 60));
            this.puzzleBoardController.showPuzzleBoard();
            this.menuPause.setSelected(false);
        } else {
            this.statusController.showMessage("The game has been over", StatusController.STATUS_ERROR);
        }
    }

    private void handleUserSolvePuzzle() {
        final String userName = (String) JOptionPane.showInputDialog(
                this,
                "Congrats! You win the game!\nPlease enter your name (leave blank to cancel):",
                "You Won",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                this.oldUserName
        );

        if (userName != null && !userName.trim().isEmpty()) {
            this.oldUserName = userName;
            try {
                this.dbHelper.insert(userName, this.tickCount);
                this.showHighScore(null);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cannot save user's achievement to database",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            } finally {
                newGame();
            }
        } else {
            newGame();
        }
    }

    private void handleUserNotSolvePuzzle() {
        JOptionPane.showMessageDialog(
                this,
                "Sorry! You didn't do well to solve the puzzle.\n"
                + "Click ok to close this box and start again.",
                "You lost",
                JOptionPane.INFORMATION_MESSAGE
        );
        newGame();
    }

    private void handleUserGaveUp() {
        final int dialogResult = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to show all the solution?",
                "Give up?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.isGameOver = true;
            this.statusController.showMessage("Game > New game to start new puzzle", StatusController.STATUS_ERROR);
            this.labelTime.setForeground(Color.red);
            this.labelTime.setText("Gave up!");
            this.timer.stop();
            this.puzzleBoardController.solveTheGame();
            this.puzzleBoardController.showPuzzleBoard();
        } else if (dialogResult == JOptionPane.NO_OPTION) {
            this.resumeGame();
        }
    }

    private void handleGameOver(int gameResult) {
        this.pauseGame();
        switch (gameResult) {
            case GAME_RESULT_TIME_UP:
                this.isGameOver = true;
                if (this.puzzleBoardController.isSolved()) {
                    handleUserSolvePuzzle();
                } else {
                    handleUserNotSolvePuzzle();
                }
                break;
            case GAME_RESULT_USER_SOLVE_PUZZLE:
                this.isGameOver = true;
                handleUserSolvePuzzle();
                break;
            case GAME_RESULT_USER_GIVE_UP:
                handleUserGaveUp();
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.timer) {
            if (this.isGameOver) {
                this.pauseGame();
            }
            try {
                this.setTickCount(tickCount + 1);
            } catch (TimeLimitExceededException ex) {
                handleGameOver(GAME_RESULT_TIME_UP);
            }
        }
    }

}

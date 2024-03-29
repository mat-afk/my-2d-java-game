package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed,
            enterPressed, shotKeyPressed, spacePressed;

    boolean checkDrawTime;

    public KeyHandler(GamePanel gp) { this.gp = gp; }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        // Title State
        if(gp.gameState == gp.titleState) { titleState(code); }

        // Play State
        else if(gp.gameState == gp.playState) { playState(code); }

        // Pause State
        else if(gp.gameState == gp.pauseState) { pauseState(code); }

        // Dialogue State
        else if(gp.gameState == gp.dialogueState) { dialogueState(code); }

        // Character status State
        else if(gp.gameState == gp.characterState) { characterState(code); }

        // Options State
        else if(gp.gameState == gp.optionsState) { optionsState(code); }

        // Game over State
        else if(gp.gameState == gp.gameOverState) { gameOverState(code); }

        // Trade State
        else if(gp.gameState == gp.tradeState) { tradeState(code); }

        // Map State
        else if(gp.gameState == gp.mapState) { mapState(code); }
    }

    public void titleState(int code) {

        if(gp.ui.titleScreenState == 0) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;
            }

            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }

            if(code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0 -> gp.ui.titleScreenState = 1;
                    case 1 -> {
                        gp.saveLoad.load();
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    case 2 -> System.exit(0);
                }
            }

        } else if(gp.ui.titleScreenState == 1) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0)
                    gp.ui.commandNum = 3;
            }

            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3)
                    gp.ui.commandNum = 0;
            }

            if(code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0, 1, 2 -> {
                        // do some class specific stuff
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    case 3 -> gp.ui.titleScreenState = 0;
                }
            }
        }
    }

    public void playState(int code) {
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if(code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
            gp.stopMusic();
        }

        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }

        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }

        if(code == KeyEvent.VK_X) {
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }

        if(code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if(code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }

        if(code == KeyEvent.VK_R) {
            switch(gp.currentMap) {
                case 0: gp.tileM.loadMap("/maps/worldV2.txt", 0);
                case 1: gp.tileM.loadMap("/maps/interior01.txt", 1);
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
            gp.resumeMusic();
        }
    }

    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    public void characterState(int code) {
        if(code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }

        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }

        playerInventory(code);
    }

    public void optionsState(int code) {

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
            gp.ui.subState = 0;
        }

        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;

        switch(gp.ui.subState) {
            case 0 -> maxCommandNum = 5;
            case 3 -> maxCommandNum = 1;
        }

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if(maxCommandNum != 0) {
                gp.ui.commandNum--;
                gp.playSoundEffect(9);
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = maxCommandNum;
                }
            }
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if(maxCommandNum != 0) {
                gp.ui.commandNum++;
                gp.playSoundEffect(9);
                if (gp.ui.commandNum > maxCommandNum) {
                    gp.ui.commandNum = 0;
                }
            }
        }

        if(code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSoundEffect(9);
                }

                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSoundEffect(9);
                }
            }
        }

        if(code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSoundEffect(9);
                }

                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSoundEffect(9);
                }
            }
        }
    }

    public void gameOverState(int code) {

        int maxCommandNum = 1;

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            gp.playSoundEffect(9);
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            gp.playSoundEffect(9);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_ENTER) {

            // Retry
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.resetGame(false);
                gp.playMusic(0);
                // Quit
            } else if(gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.ui.titleScreenState = 0;
                gp.resetGame(true);
            }
        }
    }

    public void tradeState(int code) {

        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
                gp.playSoundEffect(9);
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.playSoundEffect(9);
            }
        }

        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }

        if(gp.ui.subState == 2) {
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void mapState(int code) {

        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }

    public void playerInventory(int code) {

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if(gp.ui.playerSlotRow > gp.ui.minSlotRow) {
                gp.ui.playerSlotRow--;
                gp.playSoundEffect(9);
            }
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if(gp.ui.playerSlotRow < gp.ui.maxSlotRow) {
                gp.ui.playerSlotRow++;
                gp.playSoundEffect(9);
            }
        }

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if(gp.ui.playerSlotCol > gp.ui.minSlotCol) {
                gp.ui.playerSlotCol--;
                gp.playSoundEffect(9);
            }
        }

        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if(gp.ui.playerSlotCol < gp.ui.maxSlotCol) {
                gp.ui.playerSlotCol++;
                gp.playSoundEffect(9);
            }
        }
    }

    public void npcInventory(int code) {

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if(gp.ui.npcSlotRow > gp.ui.minSlotRow) {
                gp.ui.npcSlotRow--;
                gp.playSoundEffect(9);
            }
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if(gp.ui.npcSlotRow < gp.ui.maxSlotRow) {
                gp.ui.npcSlotRow++;
                gp.playSoundEffect(9);
            }
        }

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if(gp.ui.npcSlotCol > gp.ui.minSlotCol) {
                gp.ui.npcSlotCol--;
                gp.playSoundEffect(9);
            }
        }

        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if(gp.ui.npcSlotCol < gp.ui.maxSlotCol) {
                gp.ui.npcSlotCol++;
                gp.playSoundEffect(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false; 
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

        if(code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }

        if(code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }

        if(code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}

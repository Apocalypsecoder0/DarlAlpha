import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Map;

public class GUI extends JFrame {
    private Game game;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel topPanel;
    private JProgressBar loadingBar;
    private Timer loadingTimer;

    public GUI(Game game) {
        this.game = game;
        setTitle("Space Game");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createPanels();
        showLoadingScreen();
        createTopBar();
        createLeftPanel();
        createRightPanel();
        createMainPanel();
        createMessagePanel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createPanels() {
        setLayout(new BorderLayout());
        initializeLoadingBar();

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(getWidth(), 40));
        topPanel.setBackground(new Color(30, 30, 30));
        add(topPanel, BorderLayout.NORTH);

        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));
        leftPanel.setBackground(new Color(40, 40, 40));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        add(leftPanel, BorderLayout.WEST);

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(250, getHeight()));
        rightPanel.setBackground(new Color(40, 40, 40));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        add(rightPanel, BorderLayout.EAST);

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);
    }

    private void initializeLoadingBar() {
        loadingBar = new JProgressBar(0, 100);
        loadingBar.setStringPainted(true);
        loadingBar.setString("Loading...");
        loadingBar.setForeground(new Color(50, 200, 50));
        loadingBar.setBackground(new Color(40, 40, 40));

        loadingTimer = new Timer(30, new ActionListener() {
            int progress = 0;
            public void actionPerformed(ActionEvent e) {
                progress += 1;
                loadingBar.setValue(progress);
                if (progress >= 100) {
                    ((Timer)e.getSource()).stop();
                    remove(loadingBar);
                    showMainInterface();
                    revalidate();
                    repaint();
                }
            }
        });
    }

    private void showLoadingScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setBackground(new Color(20, 20, 20));

        JLabel titleLabel = new JLabel("Dark Alpha", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        loadingPanel.add(titleLabel, BorderLayout.CENTER);
        loadingPanel.add(loadingBar, BorderLayout.SOUTH);

        add(loadingPanel);
        loadingTimer.start();
    }

    private void showCredits() {
        JDialog creditsDialog = new JDialog(this, "Credits", true);
        creditsDialog.setSize(400, 300);
        creditsDialog.setLayout(new BorderLayout());

        JPanel creditsPanel = new JPanel();
        creditsPanel.setBackground(new Color(30, 30, 30));
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));

        String[] credits = {
                "Dark Alpha",
                "",
                "Development Team",
                "Lead Developer: [Your Name]",
                "Game Design: [Designer Name]",
                "Art: [Artist Name]",
                "",
                "Special Thanks",
                "All Beta Testers",
                "The Community"
        };

        for (String line : credits) {
            JLabel label = new JLabel(line, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (line.isEmpty()) {
                label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            }
            creditsPanel.add(label);
        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> creditsDialog.dispose());

        creditsDialog.add(new JScrollPane(creditsPanel), BorderLayout.CENTER);
        creditsDialog.add(closeButton, BorderLayout.SOUTH);
        creditsDialog.setLocationRelativeTo(this);
        creditsDialog.setVisible(true);
    }

    private void createMessagePanel() {
        JButton messageBtn = new JButton("Messages");
        messageBtn.setForeground(Color.WHITE);
        messageBtn.setBackground(new Color(60, 60, 60));
        messageBtn.addActionListener(e -> showMessagePanel());
        topPanel.add(messageBtn);
    }

    private void showMessagePanel() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel messageListPanel = new JPanel();
        messageListPanel.setLayout(new BoxLayout(messageListPanel, BoxLayout.Y_AXIS));
        messageListPanel.setBackground(new Color(30, 30, 30));

        JButton newMessageBtn = new JButton("New Message");
        newMessageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        newMessageBtn.addActionListener(e -> showNewMessageDialog());
        messageListPanel.add(newMessageBtn);

        JList<String> messageList = new JList<>(new String[] {"Message 1", "Message 2", "Message 3"});
        messageList.setBackground(new Color(40, 40, 40));
        messageList.setForeground(Color.WHITE);
        messageListPanel.add(new JScrollPane(messageList));

        mainPanel.add(messageListPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showNewMessageDialog() {
        JDialog dialog = new JDialog(this, "New Message", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);

        JPanel form = new JPanel(new GridLayout(4, 1));
        form.add(new JLabel("To:"));
        JTextField recipient = new JTextField();
        form.add(recipient);
        form.add(new JLabel("Subject:"));
        JTextField subject = new JTextField();
        form.add(subject);
        form.add(new JLabel("Message:"));
        JTextArea content = new JTextArea();
        form.add(new JScrollPane(content));

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            dialog.dispose();
        });
        form.add(sendButton);

        dialog.add(form, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAccountPage() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel accountInfo = new JPanel();
        accountInfo.setLayout(new BoxLayout(accountInfo, BoxLayout.Y_AXIS));
        accountInfo.setBackground(new Color(30, 30, 30));

        Account playerAccount = game.getPlayer().getAccount();
        BattlePass battlePass = playerAccount.getBattlePass();

        JLabel usernameLabel = new JLabel("Username: " + playerAccount.getUsername());
        usernameLabel.setForeground(Color.WHITE);
        accountInfo.add(usernameLabel);

        JProgressBar battlePassProgress = new JProgressBar(0, 100);
        battlePassProgress.setValue(battlePass.getCurrentLevel());
        battlePassProgress.setStringPainted(true);
        battlePassProgress.setString("Battle Pass Level " + battlePass.getCurrentLevel());
        accountInfo.add(battlePassProgress);

        JPanel statsPanel = new JPanel(new GridLayout(0, 2));
        statsPanel.setBackground(new Color(40, 40, 40));
        for (Map.Entry<String, Integer> stat : playerAccount.getStatistics().entrySet()) {
            JLabel statLabel = new JLabel(stat.getKey() + ": " + stat.getValue());
            statLabel.setForeground(Color.WHITE);
            statsPanel.add(statLabel);
        }

        accountInfo.add(statsPanel);
        mainPanel.add(accountInfo, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showMultiplayerLogin() {
        JDialog loginDialog = new JDialog(this, "Multiplayer Login", true);
        loginDialog.setSize(300, 200);
        loginDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginDialog.add(new JLabel("Username:"), gbc);
        loginDialog.add(username, gbc);
        loginDialog.add(new JLabel("Password:"), gbc);
        loginDialog.add(password, gbc);
        loginDialog.add(loginButton, gbc);
        loginDialog.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            game.getGameServer().login(username.getText(), new String(password.getPassword()));
            loginDialog.dispose();
        });

        registerButton.addActionListener(e -> {
            game.getGameServer().register(username.getText(), new String(password.getPassword()));
            loginDialog.dispose();
        });

        loginDialog.setLocationRelativeTo(this);
        loginDialog.setVisible(true);
    }

    private void createTopBar() {
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton multiplayerBtn = new JButton("Multiplayer");
        multiplayerBtn.setForeground(Color.WHITE);
        multiplayerBtn.setBackground(new Color(60, 60, 60));
        multiplayerBtn.addActionListener(e -> showMultiplayerLogin());
        topPanel.add(multiplayerBtn);

        JLabel resourcesLabel = new JLabel("Credits: 1000 | Energy: 500 | Minerals: 250");
        resourcesLabel.setForeground(Color.WHITE);
        topPanel.add(resourcesLabel);

        JButton mapBtn = createMenuButton("Galaxy Map");
        JButton fleetBtn = createMenuButton("Fleet");
        JButton researchBtn = createMenuButton("Research");
        JButton marketBtn = createMenuButton("Market");
        JButton accountBtn = createMenuButton("Account");
        JButton creditsBtn = createMenuButton("Credits");

        creditsBtn.addActionListener(e -> showCredits());

        accountBtn.addActionListener(e -> showAccountPage());

        topPanel.add(mapBtn);
        topPanel.add(fleetBtn);
        topPanel.add(researchBtn);
        topPanel.add(marketBtn);
    }

    private void createLeftPanel() {
        JLabel fleetTitle = createPanelTitle("Fleet Overview");
        leftPanel.add(fleetTitle);

        String[] ships = {"Battleship", "Cruiser", "Destroyer"};
        for (String ship : ships) {
            JButton shipBtn = createPanelButton(ship);
            leftPanel.add(shipBtn);
        }
    }


    private void createRightPanel() {
        JLabel infoTitle = createPanelTitle("Information");
        rightPanel.add(infoTitle);

        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(45, 45, 45));
        statsArea.setForeground(Color.WHITE);
        statsArea.setText("Player Level: 1\nFleet Size: 3\nResearch Points: 100");
        rightPanel.add(statsArea);

        JButton upgradeBtn = createPanelButton("Upgrade");
        JButton researchBtn = createPanelButton("Research");
        JButton buildBtn = createPanelButton("Build");

        rightPanel.add(upgradeBtn);
        rightPanel.add(researchBtn);
        rightPanel.add(buildBtn);
    }


    private void createMainPanel() {
        mainPanel.setLayout(new BorderLayout());

        if (game.getPlayerEmpire() == null) {
            showEmpireCreation();
            return;
        }

        if (!game.getTutorial().isComplete()) {
            showTutorialOverlay();
        }

        // Command Center Panel
        JPanel commandCenter = new JPanel(new BorderLayout());
        commandCenter.setBackground(new Color(30, 30, 40));

        // Main Action Grid (4x4)
        JPanel actionGrid = new JPanel(new GridLayout(4, 4, 5, 5));
        actionGrid.setBackground(new Color(40, 40, 50));

        // Add main action buttons
        String[] actions = {
                "Attack", "Skills", "Items", "Fleet",
                "Quest", "Trade", "Craft", "Alliance",
                "PvP", "Dungeon", "Raid", "Research",
                "Shop", "Mission", "Events", "Settings"
        };

        for (String action : actions) {
            JButton actionBtn = createActionButton(action);
            actionGrid.add(actionBtn);
        }

        // Quick Access Bar
        JPanel quickBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quickBar.setBackground(new Color(35, 35, 45));

        String[] quickActions = {"Heal", "Inventory", "Character", "Mail"};
        for (String action : quickActions) {
            JButton quickBtn = createQuickButton(action);
            quickBar.add(quickBtn);
        }

        // Status Panel
        JPanel statusPanel = new JPanel(new GridLayout(2, 4));
        statusPanel.setBackground(new Color(25, 25, 35));

        addStatusBar(statusPanel, "HP", 100, Color.RED);
        addStatusBar(statusPanel, "MP", 100, Color.BLUE);
        addStatusBar(statusPanel, "EXP", 75, Color.GREEN);
        addStatusBar(statusPanel, "Energy", 50, Color.YELLOW);

        commandCenter.add(quickBar, BorderLayout.NORTH);
        commandCenter.add(actionGrid, BorderLayout.CENTER);
        commandCenter.add(statusPanel, BorderLayout.SOUTH);

        mainPanel.add(commandCenter, BorderLayout.CENTER);
    }


    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(60, 60, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 100), 2));
        button.addActionListener(e -> handleActionButton(text));

        // Add tooltip based on action type
        String tooltip = switch(text) {
            case "Attack" -> "Engage in combat with enemy ships";
            case "Skills" -> "View and upgrade your empire's abilities";
            case "Items" -> "Manage your inventory and equipment";
            case "Fleet" -> "View and manage your fleet of ships";
            case "Quest" -> "View available missions and rewards";
            case "Trade" -> "Access the galactic marketplace";
            case "Craft" -> "Create new items and equipment";
            case "Alliance" -> "Manage your alliance relationships";
            case "PvP" -> "Engage in player versus player combat";
            case "Dungeon" -> "Explore dangerous space dungeons";
            case "Raid" -> "Join epic raid battles";
            case "Research" -> "Develop new technologies";
            case "Shop" -> "Purchase items and upgrades";
            case "Mission" -> "View active missions";
            case "Events" -> "Check special galaxy events";
            case "Settings" -> "Configure game options";
            default -> text;
        };
        button.setToolTipText(tooltip);
        return button;
    }


    private JButton createQuickButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(50, 50, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
        button.addActionListener(e -> handleQuickAction(text));

        // Add tooltip for quick actions
        String tooltip = switch(text) {
            case "Heal" -> "Quickly restore ship health";
            case "Inventory" -> "Quick access to your inventory";
            case "Character" -> "View character stats and equipment";
            case "Mail" -> "Check your messages";
            default -> text;
        };
        button.setToolTipText(tooltip);
        return button;
    }


    private void addStatusBar(JPanel panel, String label, int value, Color color) {
        JLabel nameLabel = new JLabel(label);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        bar.setStringPainted(true);
        bar.setForeground(color);
        bar.setBackground(new Color(30, 30, 40));

        panel.add(nameLabel);
        panel.add(bar);
    }


    private void handleActionButton(String action) {
        switch(action) {
            case "Attack":
                showCombatPanel();
                break;
            case "Skills":
                showSkillsPanel();
                break;
            case "Items":
                showInventoryPanel();
                break;
            case "Fleet":
                showFleetPanel();
                break;
            case "Settings":
                showSettingsPanel();
                break;
            default:
                mainPanel.removeAll();
                JLabel actionLabel = new JLabel(action + " Panel", SwingConstants.CENTER);
                actionLabel.setForeground(Color.WHITE);
                mainPanel.add(actionLabel);
                mainPanel.revalidate();
                mainPanel.repaint();
        }
    }

    private void showSettingsPanel() {
        mainPanel.removeAll();
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBackground(new Color(30, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Audio Settings
        JPanel audioPanel = createSettingsSection("Audio Settings");
        JSlider musicVolume = new JSlider(0, 100, 50);
        JSlider sfxVolume = new JSlider(0, 100, 50);
        audioPanel.add(new JLabel("Music Volume"));
        audioPanel.add(musicVolume);
        audioPanel.add(new JLabel("SFX Volume"));
        audioPanel.add(sfxVolume);
        settingsPanel.add(audioPanel, gbc);

        // Graphics Settings
        JPanel graphicsPanel = createSettingsSection("Graphics Settings");
        String[] qualities = {"Low", "Medium", "High", "Ultra"};
        JComboBox<String> graphicsQuality = new JComboBox<>(qualities);
        JCheckBox fullscreen = new JCheckBox("Fullscreen");
        graphicsPanel.add(new JLabel("Quality"));
        graphicsPanel.add(graphicsQuality);
        graphicsPanel.add(fullscreen);
        settingsPanel.add(graphicsPanel, gbc);

        // Gameplay Settings
        JPanel gameplayPanel = createSettingsSection("Gameplay Settings");
        JCheckBox tooltips = new JCheckBox("Show Tooltips");
        JCheckBox tutorials = new JCheckBox("Show Tutorials");
        JCheckBox autoSave = new JCheckBox("Auto Save");
        tooltips.setSelected(true);
        tutorials.setSelected(true);
        autoSave.setSelected(true);
        gameplayPanel.add(tooltips);
        gameplayPanel.add(tutorials);
        gameplayPanel.add(autoSave);
        settingsPanel.add(gameplayPanel, gbc);

        // Controls
        JPanel controlsPanel = createSettingsSection("Controls");
        JButton keybinds = new JButton("Configure Keybinds");
        controlsPanel.add(keybinds);
        settingsPanel.add(controlsPanel, gbc);

        // Save Button
        JButton saveSettings = new JButton("Save Settings");
        saveSettings.addActionListener(e -> saveGameSettings());
        settingsPanel.add(saveSettings, gbc);

        mainPanel.add(new JScrollPane(settingsPanel));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createSettingsSection(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                null,
                Color.WHITE
        ));
        panel.setBackground(new Color(40, 40, 50));
        return panel;
    }

    private void saveGameSettings() {
        // Implement settings save logic
        JOptionPane.showMessageDialog(this, "Settings saved successfully!");
    }


    private void handleQuickAction(String action) {
        System.out.println("Quick action: " + action);
        // Implement quick action handlers
    }

    private JLabel createPanelTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    private void handleMenuClick(String menuItem) {
        mainPanel.removeAll();

        JLabel contentLabel = new JLabel(menuItem + " View", SwingConstants.CENTER);
        contentLabel.setForeground(Color.WHITE);
        contentLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(contentLabel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 60, 60));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> handleMenuClick(text));
        return button;
    }

    private JButton createPanelButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 50));
        button.setMaximumSize(new Dimension(190, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    private void performSystemScan() {
        mainPanel.removeAll();
        JTextArea scanResults = new JTextArea("Scanning system...\n");
        scanResults.setEditable(false);
        mainPanel.add(new JScrollPane(scanResults));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void openCommunications() {
        mainPanel.removeAll();
        JPanel commsPanel = new JPanel(new BorderLayout());
        commsPanel.setBackground(new Color(30, 30, 40));

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        commsPanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        mainPanel.add(commsPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showShipStatus() {
        mainPanel.removeAll();
        JPanel statusPanel = new JPanel(new GridLayout(0, 2));
        statusPanel.setBackground(new Color(30, 30, 40));

        Fleet playerFleet = game.getPlayer().getFleet();
        for (Ship ship : playerFleet.getShips()) {
            addStatusLabel(statusPanel, "Ship Name", ship.getName());
            addStatusLabel(statusPanel, "Health", ship.getHealth() + "");
            addStatusLabel(statusPanel, "Shields", ship.getShields() + "");
        }

        mainPanel.add(new JScrollPane(statusPanel));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showSaveGameDialog() {
        JDialog dialog = new JDialog(this, "Save Game", true);
        dialog.setLayout(new GridLayout(6, 1, 5, 5));

        for (int i = 1; i <= 5; i++) {
            final int slot = i;
            JButton saveSlot = new JButton("Save Slot " + i);
            saveSlot.addActionListener(e -> {
                try {
                    game.getSaveSystem().saveGame(game, slot);
                    dialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error saving game: " + ex.getMessage());
                }
            });
            dialog.add(saveSlot);
        }

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showLoadGameDialog() {
        JDialog dialog = new JDialog(this, "Load Game", true);
        dialog.setLayout(new GridLayout(6, 1, 5, 5));

        for (int i = 1; i <= 5; i++) {
            final int slot = i;
            JButton loadSlot = new JButton("Load Slot " + i);
            loadSlot.addActionListener(e -> {
                SaveSystem.GameData gameData = game.getSaveSystem().loadGame(slot);
                if (gameData != null) {
                    // Handle loading game data
                    dialog.dispose();
                }
            });
            dialog.add(loadSlot);
        }

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void activateBattleStations() {
        mainPanel.removeAll();
        JLabel battleLabel = new JLabel("Battle Stations Activated!", SwingConstants.CENTER);
        battleLabel.setForeground(Color.RED);
        battleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(battleLabel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showCombatPanel(){}
    private void showSkillsPanel(){}
    private void showInventoryPanel(){}
    private void showFleetPanel(){}
    private void addStatusLabel(JPanel panel, String label, String value) {
        JLabel l = new JLabel(label + ":", SwingConstants.RIGHT);
        l.setForeground(Color.WHITE);
        panel.add(l);

        JLabel v = new JLabel(value, SwingConstants.LEFT);
        v.setForeground(Color.GREEN);
        panel.add(v);
    }

    private void addCommandButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(new Color(60, 60, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(action);
        panel.add(button);
    }
    private void showEmpireCreation() {
        mainPanel.removeAll();
        JPanel empirePanel = new JPanel(new GridBagLayout());
        empirePanel.setBackground(new Color(30, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Create Your Empire", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        empirePanel.add(titleLabel, gbc);

        JTextField empireName = new JTextField(20);
        empireName.setPreferredSize(new Dimension(200, 30));
        empirePanel.add(new JLabel("Empire Name:"), gbc);
        empirePanel.add(empireName, gbc);

        JComboBox<StarTrekRace> raceSelection = new JComboBox<>(StarTrekRace.values());
        empirePanel.add(new JLabel("Choose Race:"), gbc);
        empirePanel.add(raceSelection, gbc);

        JButton createButton = new JButton("Found Empire");
        createButton.addActionListener(e -> {
            String name = empireName.getText();
            if (!name.isEmpty()) {
                game.createPlayerEmpire(name, (StarTrekRace)raceSelection.getSelectedItem());
                createMainPanel();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        empirePanel.add(createButton, gbc);

        mainPanel.add(empirePanel, BorderLayout.CENTER);
    }

    private void showMainInterface() {
        //Remove loading screen
        getContentPane().removeAll();
        createPanels();
        createTopBar();
        createLeftPanel();
        createRightPanel();
        createMainPanel();
        createMessagePanel();
        revalidate();
        repaint();
    }

    private void showTutorialOverlay() {
        TutorialStep currentStep = game.getTutorial().getCurrentStep();
        if (currentStep == null) return;

        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setBackground(new Color(0, 0, 0, 150));

        JPanel tutorialPanel = new JPanel();
        tutorialPanel.setBackground(new Color(40, 40, 60));
        tutorialPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JLabel titleLabel = new JLabel(currentStep.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel descLabel = new JLabel(currentStep.getDescription());
        descLabel.setForeground(Color.WHITE);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            game.getTutorial().progressTutorial();
            mainPanel.remove(overlay);
            if (!game.getTutorial().isComplete()) {
                showTutorialOverlay();
            }
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        tutorialPanel.add(titleLabel);
        tutorialPanel.add(descLabel);
        tutorialPanel.add(nextButton);

        overlay.add(tutorialPanel, BorderLayout.SOUTH);
        mainPanel.add(overlay, BorderLayout.CENTER);
    }
}
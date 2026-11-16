
package ui;

import facade.RestaurantFacade;
import models.Meal;
import models.Order;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Locale;

/** Swing user interface for the Restaurant Ordering System. */
public class RestaurantWindow {

    private static final Color NAVY =
            new Color(31, 41, 55);

    private static final Color BACKGROUND =
            new Color(245, 247, 250);

    private static final Color ACCENT =
            new Color(38, 123, 96);

    private static final Color MUTED =
            new Color(107, 114, 128);

    private static final Color BORDER =
            new Color(225, 229, 235);

    private final JFrame frame;
    private final RestaurantFacade facade;

    private final DefaultTableModel cartModel;
    private final JTable cartTable;

    private final JComboBox<String> discountBox;

    private final JLabel subtotalValue;
    private final JLabel discountValue;
    private final JLabel totalValue;
    private final JLabel statusLabel;

    public RestaurantWindow(RestaurantFacade facade) {

        this.facade = facade;
        this.frame = new JFrame("Restaurant Ordering System");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(980, 660));
        frame.setSize(1160, 760);
        frame.setLocationRelativeTo(null);

        cartModel = new DefaultTableModel(
                new Object[]{"Meal", "Price (KZT)"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(34);
        cartTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cartTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        cartTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13));

        cartTable.setFillsViewportHeight(true);

        discountBox = new JComboBox<>(new String[]{
                "No discount",
                "Student - 30% (max 2,000 KZT)",
                "Staff - 5,000 KZT"
        });

        discountBox.setFont(
                new Font("SansSerif", Font.PLAIN, 12));

        subtotalValue = new JLabel("0 KZT");
        discountValue = new JLabel("0 KZT");
        totalValue = new JLabel("0 KZT");

        statusLabel = new JLabel("Ready to take your order");

        buildInterface();
        connectEvents();
        refreshCart();

        facade.addObserver(status -> SwingUtilities.invokeLater(
                () -> statusLabel.setText("Order update: " + status)));
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void buildInterface() {

        JPanel root = new JPanel(
                new BorderLayout(16, 16));

        root.setBackground(BACKGROUND);

        root.setBorder(
                BorderFactory.createEmptyBorder(16, 16, 12, 16));

        frame.setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);

        // Menu receives 56% of the available width.
        // Cart receives 44%.

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        GridBagConstraints constraints =
                new GridBagConstraints();

        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;

        constraints.insets = new Insets(0, 0, 0, 9);

        constraints.gridx = 0;
        constraints.weightx = 0.56;

        content.add(createMenuPanel(), constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.44;
        constraints.insets = new Insets(0, 9, 0, 0);

        content.add(createCartPanel(), constraints);

        root.add(content, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);

        statusLabel.setForeground(MUTED);
        statusLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 12));

        footer.add(statusLabel, BorderLayout.WEST);

        JLabel note = new JLabel(
                "Demo application  |  Prices in KZT");

        note.setForeground(MUTED);
        note.setFont(
                new Font("SansSerif", Font.PLAIN, 12));

        footer.add(note, BorderLayout.EAST);

        root.add(footer, BorderLayout.SOUTH);
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(
                new BorderLayout(15, 0));

        header.setBackground(NAVY);

        header.setBorder(
                BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel title = new JLabel(
                "Restaurant Ordering System");

        title.setForeground(Color.WHITE);
        title.setFont(
                new Font("SansSerif", Font.BOLD, 25));

        JLabel subtitle = new JLabel(
                "Choose a meal, add your extras, and place your order");

        subtitle.setForeground(new Color(214, 220, 228));
        subtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 13));

        JPanel titles = new JPanel(
                new GridLayout(2, 1, 0, 5));

        titles.setOpaque(false);
        titles.add(title);
        titles.add(subtitle);

        header.add(titles, BorderLayout.CENTER);

        JLabel tag = new JLabel(
                "FRESH  •  SIMPLE  •  DELICIOUS");

        tag.setForeground(new Color(190, 230, 213));
        tag.setFont(
                new Font("SansSerif", Font.BOLD, 11));

        header.add(tag, BorderLayout.EAST);

        return header;
    }

    private JPanel createMenuPanel() {

        JPanel panel = createSectionPanel(
                "MENU",
                "Choose a meal and customize it");

        // Important: cards are now side by side.
        JPanel cards = new JPanel(
                new GridLayout(1, 2, 14, 0));

        cards.setOpaque(false);

        cards.add(createMealCard(
                "Pizza",
                "Classic pizza",
                2500,
                "pizza"));

        cards.add(createMealCard(
                "Burger",
                "Grilled burger",
                1800,
                "burger"));

        panel.add(cards, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMealCard(
            String name,
            String description,
            int price,
            String mealKey) {

        JPanel card = new JPanel(
                new BorderLayout(0, 12));

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(
                                10, 10, 12, 10)));

        // Larger photos that fill the card width.
        JLabel photo = ImageLoader.createFoodImage(
                mealKey + ".png",
                320,
                225);

        card.add(photo, BorderLayout.NORTH);

        JLabel mealName = new JLabel(name);

        mealName.setFont(
                new Font("SansSerif", Font.BOLD, 21));

        mealName.setForeground(NAVY);

        JLabel mealDescription = new JLabel(description);

        mealDescription.setForeground(MUTED);
        mealDescription.setFont(
                new Font("SansSerif", Font.PLAIN, 13));

        JLabel mealPrice = new JLabel(
                String.format(Locale.US, "%,d KZT", price));

        mealPrice.setFont(
                new Font("SansSerif", Font.BOLD, 18));

        mealPrice.setForeground(ACCENT);

        JPanel details = new JPanel(
                new GridLayout(3, 1, 0, 5));

        details.setOpaque(false);

        details.setBorder(
                BorderFactory.createEmptyBorder(4, 3, 3, 3));

        details.add(mealName);
        details.add(mealDescription);
        details.add(mealPrice);

        JButton addButton = new JButton(
                "Customize and add");

        stylePrimaryButton(addButton);

        addButton.addActionListener(
                event -> showCustomizationDialog(mealKey, name));

        card.add(details, BorderLayout.CENTER);
        card.add(addButton, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createCartPanel() {

        JPanel panel = createSectionPanel(
                "YOUR ORDER",
                "Review items and choose a discount");

        JScrollPane scrollPane = new JScrollPane(cartTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER));

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(
                new BorderLayout(0, 12));

        bottom.setOpaque(false);

        JPanel actions = new JPanel(
                new GridLayout(1, 2, 8, 0));

        actions.setOpaque(false);

        JButton removeButton = new JButton(
                "Remove selected");

        removeButton.setFont(
                new Font("SansSerif", Font.PLAIN, 12));

        removeButton.addActionListener(
                event -> removeSelectedMeal());

        actions.add(removeButton);
        actions.add(discountBox);

        bottom.add(actions, BorderLayout.NORTH);

        JPanel totals = new JPanel(
                new GridLayout(3, 2, 8, 8));

        totals.setOpaque(false);

        totals.setBorder(
                BorderFactory.createEmptyBorder(7, 2, 5, 2));

        totals.add(makeTotalLabel("Subtotal"));
        totals.add(subtotalValue);

        totals.add(makeTotalLabel("Discount"));
        totals.add(discountValue);

        JLabel totalLabel = makeTotalLabel("TOTAL");

        totalLabel.setFont(
                new Font("SansSerif", Font.BOLD, 16));

        totalValue.setFont(
                new Font("SansSerif", Font.BOLD, 19));

        totalValue.setForeground(ACCENT);

        totals.add(totalLabel);
        totals.add(totalValue);

        bottom.add(totals, BorderLayout.CENTER);

        JButton checkoutButton = new JButton("Place order");

        stylePrimaryButton(checkoutButton);

        checkoutButton.addActionListener(
                event -> checkout());

        bottom.add(checkoutButton, BorderLayout.SOUTH);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSectionPanel(
            String title,
            String subtitle) {

        JPanel panel = new JPanel(
                new BorderLayout(0, 14));

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(
                                16, 16, 16, 16)));

        JLabel heading = new JLabel(title);

        heading.setFont(
                new Font("SansSerif", Font.BOLD, 15));

        heading.setForeground(NAVY);

        JLabel subheading = new JLabel(subtitle);

        subheading.setFont(
                new Font("SansSerif", Font.PLAIN, 12));

        subheading.setForeground(MUTED);

        JPanel headings = new JPanel(
                new GridLayout(2, 1, 0, 4));

        headings.setOpaque(false);

        headings.add(heading);
        headings.add(subheading);

        panel.add(headings, BorderLayout.NORTH);

        return panel;
    }

    private JLabel makeTotalLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(NAVY);

        label.setFont(
                new Font("SansSerif", Font.PLAIN, 13));

        return label;
    }

    private void stylePrimaryButton(JButton button) {

        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 13));

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        11, 14, 11, 14));
    }

    private void connectEvents() {

        discountBox.addActionListener(event -> {

            switch (discountBox.getSelectedIndex()) {

                case 1:
                    facade.setStudentDiscount();
                    break;

                case 2:
                    facade.setStaffDiscount();
                    break;

                default:
                    facade.setNoDiscount();
                    break;
            }

            updateTotals();
        });
    }

    private void showCustomizationDialog(
            String mealKey,
            String mealName) {

        JCheckBox cheese = new JCheckBox(
                "Cheese (+300 KZT)");

        JCheckBox bacon = new JCheckBox(
                "Bacon (+500 KZT)");

        JCheckBox sauce = new JCheckBox(
                "Extra sauce (+150 KZT)");

        JPanel options = new JPanel(
                new GridLayout(0, 1, 4, 4));

        options.add(
                new JLabel("Customize your " + mealName + ":"));

        options.add(cheese);
        options.add(bacon);
        options.add(sauce);

        int result = JOptionPane.showConfirmDialog(
                frame,
                options,
                "Meal customization",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        Meal meal = facade.addCustomizedMeal(
                mealKey,
                cheese.isSelected(),
                bacon.isSelected(),
                sauce.isSelected());

        statusLabel.setText(
                meal.getName() + " added to your order");

        refreshCart();
    }

    private void removeSelectedMeal() {

        int selectedRow = cartTable.getSelectedRow();

        if (selectedRow < 0) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Select an item to remove first.",
                    "No item selected",
                    JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        facade.removeMeal(selectedRow);

        statusLabel.setText(
                "Item removed from your order");

        refreshCart();
    }

    private void refreshCart() {

        cartModel.setRowCount(0);

        for (Meal meal : facade.getCurrentMeals()) {

            cartModel.addRow(new Object[]{
                    meal.getName(),
                    formatPrice(meal.getPrice())
            });
        }

        updateTotals();
    }

    private void updateTotals() {

        subtotalValue.setText(
                formatPrice(facade.getSubtotal()));

        discountValue.setText(
                "-" + formatPrice(facade.getDiscountAmount()));

        totalValue.setText(
                formatPrice(facade.getFinalTotal()));
    }

    private void checkout() {

        if (facade.getCurrentMeals().isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Your order is empty. Add a meal first.",
                    "Nothing to checkout",
                    JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        double subtotal = facade.getSubtotal();
        double discount = facade.getDiscountAmount();
        double total = facade.getFinalTotal();

        String confirmation =
                "Subtotal: " + formatPrice(subtotal)
                        + "\nDiscount: " + formatPrice(discount)
                        + "\nTotal: " + formatPrice(total)
                        + "\n\nPlace this order?";

        int answer = JOptionPane.showConfirmDialog(
                frame,
                confirmation,
                "Confirm order",
                JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        Order order = facade.completeOrder();

        StringBuilder receipt = new StringBuilder();

        receipt.append("Order #")
                .append(order.getId())
                .append("\n\n");

        for (Meal meal : order.getMeals()) {

            receipt.append(meal.getName())
                    .append(" — ")
                    .append(formatPrice(meal.getPrice()))
                    .append(" KZT\n");
        }

        receipt.append("\nSubtotal: ")
                .append(formatPrice(subtotal))
                .append(" KZT");

        receipt.append("\nDiscount: ")
                .append(formatPrice(discount))
                .append(" KZT");

        receipt.append("\nTOTAL: ")
                .append(formatPrice(total))
                .append(" KZT");

        receipt.append("\n\nThank you for your order!");

        JOptionPane.showMessageDialog(
                frame,
                receipt.toString(),
                "Order placed",
                JOptionPane.INFORMATION_MESSAGE);

        facade.setNoDiscount();

        discountBox.setSelectedIndex(0);

        refreshCart();

        statusLabel.setText(
                "Order #" + order.getId()
                        + " placed successfully");
    }

    private static String formatPrice(double price) {

        return String.format(
                Locale.US,
                "%,.0f KZT",
                price);
    }
}

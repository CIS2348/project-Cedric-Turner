package view;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

// Author: Cedric Turner
// CIS 2348

public final class ShoppingFrame extends JFrame {

    
    private static final long serialVersionUID = 0;

    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    private static final int TEXT_FIELD_WIDTH = 12;
    private static final Color COLOR_1 = new Color(199, 153, 0); // UW Gold

    private static final Color COLOR_2 = new Color(57, 39, 91); // UW Purple

    private final ShoppingCart myItems;

    private final JTextField myTotal;

    private final List<JTextField> myQuantities;

    public ShoppingFrame(final List<Item> theItems) {
        // create frame and order list
        super(); // no title on the JFrame

        myItems = new ShoppingCart();

        // set up text field with order total
        myTotal = new JTextField("$0.00", TEXT_FIELD_WIDTH);

        myQuantities = new LinkedList<>();

        setupGUI(theItems);
    }

    private void setupGUI(final List<Item> theItems) {
        // hide the default JFrame icon
        final Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(makeTotalPanel(), BorderLayout.NORTH);
        add(makeItemsPanel(theItems), BorderLayout.CENTER);
        add(makeCheckBoxPanel(), BorderLayout.SOUTH);

        // adjust size to just fit
        pack();

        // position the frame in the center of the screen
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                SCREEN_SIZE.height / 2 - getHeight() / 2);
        setVisible(true);
    }


    private JPanel makeTotalPanel() {
        // tweak the text field so that users can't edit it, and set
        // its color appropriately

        myTotal.setEditable(false);
        myTotal.setEnabled(false);
        myTotal.setDisabledTextColor(Color.BLACK);

        // create the panel, and its label

        final JPanel p = new JPanel();
        p.setBackground(COLOR_2);
        final JLabel l = new JLabel("order total");
        l.setForeground(Color.WHITE);
        p.add(l);
        p.add(myTotal);
        return p;
    }

    private JPanel makeItemsPanel(final List<Item> theItems) {
        final JPanel p = new JPanel(new GridLayout(theItems.size(), 1));

        for (final Item item : theItems) {
            addItem(item, p);
        }

        return p;
    }

    private JPanel makeCheckBoxPanel() {
        final JPanel p = new JPanel();
        p.setBackground(COLOR_2);

        final JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myItems.clear();
                for (final JTextField field : myQuantities) {
                    field.setText("");
                }
                updateTotal();
            }
        });
        p.add(clearButton);

        final JCheckBox cb = new JCheckBox("customer has store membership. they will receive a discount");
        cb.setForeground(Color.BLACK);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myItems.setMembership(cb.isSelected());
                updateTotal();
            }
        });
        p.add(cb);

        return p;
    }
    private void addItem(final Item theItem, final JPanel thePanel) {
        final JPanel sub = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sub.setBackground(COLOR_1);
        final JTextField quantity = new JTextField(3);
        myQuantities.add(quantity);
        quantity.setHorizontalAlignment(SwingConstants.CENTER);
        quantity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                quantity.transferFocus();
            }
        });
        quantity.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent theEvent) {
                updateItem(theItem, quantity);
            }
        });
        sub.add(quantity);
        final JLabel l = new JLabel(theItem.toString());
        l.setForeground(COLOR_2);
        sub.add(l);
        thePanel.add(sub);
    }
    private void updateItem(final Item theItem, final JTextField theQuantity) {
        final String text = theQuantity.getText().trim();
        int number;
        try {
            number = Integer.parseInt(text);
            if (number < 0) {
                // disallow negative numbers
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException e) {
            number = 0;
            theQuantity.setText("");
        }
        myItems.add(new ItemOrder(theItem, number));
        updateTotal();
    }


    private void updateTotal() {
        final double total = myItems.calculateTotal().doubleValue();
        myTotal.setText(NumberFormat.getCurrencyInstance().format(total));
    }
}

// end of class ShoppingFrame
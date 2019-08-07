package assignmentseven;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/******************************************************************************
 *                                 Main.java                                  *
 * Author:      David Mills                                                   *
 * Version:     1.0.2                                                         *
 * Date:        Mar 7, 2019                                                   *
 *                                                                            *
 * Description:                                                               *
 *      This is the runnable GUI instance that allows the user to create an   *
 *      array of InventoryItem objects of size 5 and search using both a      *
 *      linear search and binary search (after sorting using a merge sort) to *
 *      find a specific InventoryItem via the UPC.                            *
 *                                                                            *
 *      This was written for Forsyth Technical Community College CSC-251.     *
 *                                                                            *
 * Version History:                                                           *
 *           1.0.0:                                                           *
 *      Started the program. Set the main method to use the SwingUtilities'   *
 *      invokeLater method using a lambda method for the runnable instance    *
 *      parameter. Also wrote the linear search algorithm.                    *
 *           1.0.1:                                                           *
 *      Wrote the merge sort algorithm. I actually wrote it myself with the   *
 *      aid of drawing how it is supposed to work. I'm pretty proud of it.    *
 *      Also I wrote the linear search and set up the actual GUI to display   *
 *      results and take in input.                                            *
 *           1.0.2:                                                           *
 *      Wrote the binary search algorithm and implemented it with the GUI.    *
 *      Ready to submit.                                                      *
 *                                                                            *
 * @author David Mills                                                        *
 * @version 1.0.2                                                             *
 ******************************************************************************/
public class Main extends JFrame
{
    // Class constants and various class variables.
    private static final int SIZE = 5;
    private static int currentPosition = 1;
    private InventoryItem[] allItems;
    
    // Creating JLabels.
    private final JLabel lblIntro;
    private final JLabel lblIntroTwo;
    private final JLabel lblUpc;
    private final JLabel lblDescription;
    private final JLabel lblPrice;
    private final JLabel lblResults;
    private final JLabel lblPrompt;
    
    // Creating JTextFields.
    private final JTextField txtUpc;
    private final JTextField txtDescription;
    private final JTextField txtPrice;
    private final JTextField txtSearch;
    
    // Creating JButtons.
    private final JButton btnSearch;
    private final JButton btnCreate;
    private final JButton btnDisplay;
    
    // Creating JPanels.
    private final JPanel pnlMain;
    private final JPanel pnlIntro;
    private final JPanel pnlUserInput;
    private final JPanel pnlResults;
    private final JPanel pnlSearch;
    private final JPanel pnlButtons;
    
    /**
     * This is the constructor where the magic is made.
     */
    public Main()
    {
        // Initial setting up of the frame.
        allItems = new InventoryItem[SIZE];
        setTitle("Search and Sort Testing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //********************************JLabels*******************************
        lblIntro = new JLabel("Enter the following information for");
        lblIntro.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblIntroTwo = new JLabel("the inventory items:");
        lblIntroTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblUpc = new JLabel("UPC:");
        lblDescription = new JLabel("Description:");
        lblPrice = new JLabel("Price:");
        
        lblResults = new JLabel();
        lblResults.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblPrompt = new JLabel("Enter a UPC to search for:");
        lblPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //******************************JTextFields*****************************
        txtUpc = new JTextField(10);
        txtDescription = new JTextField(10);
        txtPrice = new JTextField(10);
        txtSearch = new JTextField(10);
        txtSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //********************************JButtons******************************
        btnSearch = new JButton("Linear Search");
        btnCreate = new JButton("Create");
        btnSearch.setVisible(false);
        // Lambda method for the ActionEvent to add to the ActionListener for
        // the search button. It does different searches depending on if the
        // list is sorted or not.
        btnSearch.addActionListener((ActionEvent) -> 
        {
            if (txtSearch.getText().isEmpty())
            {
                lblResults.setText("Error! Search text field empty!");
                txtSearch.grabFocus();
            }
            else
            {
                int searchResults = (btnSearch.getText().charAt(0) == 'L' 
                        ? LinearSearch(txtSearch.getText()) 
                        : BinarySearch(allItems, 0, allItems.length - 1,
                                txtSearch.getText()));
                if (searchResults == -1)
                    lblResults.setText("No UPC matches " 
                            + txtSearch.getText() + ".");
                else
                    lblResults.setText("<html>Success! "
                            + "Results of your search are below:<br />" 
                            + allItems[searchResults]
                                    .toString()
                                    .replace("\n", "<br />") 
                            + "</html>");
                if (btnSearch.getText().charAt(0) == 'L') 
                    btnCreate.setEnabled(true);
                else 
                    btnCreate.setEnabled(false);
            }
            pack();
        });
        
        btnDisplay = new JButton("Display");
        btnDisplay.setEnabled(false);
        // Lambda method for the ActionEvent to add to the ActionListener for
        // the display button.
        btnDisplay.addActionListener((ActionEvent) ->
        {
            // Build the results text with the use of a StringBuilder.
            StringBuilder results = new StringBuilder();
            if (currentPosition < SIZE)
                results.append("Here's what we have so far:\n");
            else
                results.append("Here is the completed list:\n");
            // Here is where I add the formatting to the list of elements.
            for (int i = 0; i < SIZE; i++)
                if (allItems[i] != null)
                    results.append("Item # ")
                            .append(i + 1)
                            .append("\n")
                            .append(allItems[i].toString())
                            .append("\n\n");
            // Finally, I have to add some <br /> tags in place of new line
            // characters and enclose the String in html opening and closing
            // tags.
            results.insert(0, "<html>");
            results.append("</html>");
            String resultsString = results.toString();
            resultsString = resultsString.replace("\n", "<br />");
            lblResults.setText(resultsString);
            pack();
        });
        
        // Had to create the panels here to allow them to be changed through
        // the action event here.
        pnlSearch = new JPanel();
        pnlUserInput = new JPanel();
        // Lambda method for the ActionEvent to add to the ActionListener for
        // the create button.
        btnCreate.addActionListener((ActionEvent) -> 
        {
            // I gave this button two different functionalities.
            // If it is in state "Create" fall here.
            if (btnCreate.getText().equals("Create"))
            {
                // Try to make an InventoryItem with the current values.
                try
                {
                    allItems[currentPosition - 1] 
                            = new InventoryItem(txtUpc.getText(),
                            txtDescription.getText(),
                            Double.parseDouble(txtPrice.getText()));
                    lblResults.setText("Success! Position " + currentPosition 
                            + " set. " + (SIZE - currentPosition) 
                            + " more to go.");
                    if (!btnDisplay.isEnabled())
                        btnDisplay.setEnabled(true);
                    // If all of the positions are filled, change this button's
                    // functionality and change the visibility of certain
                    // components on the JFrame.
                    if (currentPosition == SIZE)
                    {
                        btnCreate.setText("Merge Sort");
                        btnCreate.setEnabled(false);
                        lblResults.setText("Success! All positions filled!");
                        pnlSearch.setVisible(true);
                        btnSearch.setVisible(true);
                        pnlUserInput.setVisible(false);
                        lblIntro.setText("Choose and option below:");
                        lblIntroTwo.setText("");
                    }
                    currentPosition++;
                }
                // Multiple catches depending on the error thrown.
                // Because all of the following exceptions inherit from
                // IllegalArgumentException, I had to stagger them in this
                // manner.
                catch (EmptyArgumentException ex)
                {
                    lblResults.setText("Error! " + ex.getMessage());
                    // Depending on which error was thrown, the first character
                    // of the message will be different. Grab focus of whichever
                    // was the cause of the error.
                    switch (ex.getMessage().charAt(0))
                    {
                        case 'U':
                            txtUpc.grabFocus();
                            break;
                        case 'D':
                            txtDescription.grabFocus();
                            break;
                    }
                }
                // It falls here if the price is not a number or if the user
                // does not fill out the field.
                catch (NumberFormatException ex)
                {
                    lblResults.setText("Error! "
                            + "Price must be a decimal number!");
                    txtPrice.grabFocus();
                    txtPrice.selectAll();
                }
                // It falls here if the user enters a negative number or zero 
                // for the price.
                catch (IllegalArgumentException ex)
                {
                    lblResults.setText("Error! " + ex.getMessage());
                    txtPrice.grabFocus();
                    txtPrice.selectAll();
                }
            }
            // If the functionality is to sort, sort it and display the results.
            else
            {
                MergeSort(allItems, 0, SIZE - 1);
                btnDisplay.doClick();
                btnCreate.setEnabled(false);
                btnSearch.setText("Binary Search");
            }
            // No matter what, repack the frame.
            pack();
        });
        
        
        //********************************JPanels*******************************
        pnlMain = new JPanel();
        
        pnlIntro = new JPanel();
        pnlIntro.setLayout(new BoxLayout(pnlIntro, BoxLayout.Y_AXIS));
        pnlIntro.add(lblIntro);
        pnlIntro.add(lblIntroTwo);
        pnlIntro.add(Box.createRigidArea(new Dimension(0, 10)));
        
        pnlUserInput.setLayout(new GridLayout(3, 2, 10, 10));
        pnlUserInput.add(lblUpc);
        pnlUserInput.add(txtUpc);
        pnlUserInput.add(lblDescription);
        pnlUserInput.add(txtDescription);
        pnlUserInput.add(lblPrice);
        pnlUserInput.add(txtPrice);
        
        pnlResults = new JPanel();
        pnlResults.add(lblResults);
        
        pnlSearch.setLayout(new BoxLayout(pnlSearch, BoxLayout.Y_AXIS));
        pnlSearch.add(lblPrompt);
        pnlSearch.add(txtSearch);
        pnlSearch.setVisible(false);
        
        pnlButtons = new JPanel();
        pnlButtons.add(btnSearch);
        pnlButtons.add(btnCreate);
        pnlButtons.add(btnDisplay);
        
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.add(pnlIntro);
        pnlMain.add(pnlUserInput);
        pnlMain.add(pnlResults);
        pnlMain.add(pnlSearch);
        pnlMain.add(pnlButtons);
        
        //********************************JFrame********************************
        add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
        add(pnlMain, BorderLayout.CENTER);
        pack();
    }
    
    /**
     * Performs a linear search of the InventoryItem array to find a 
     * user-defined UPC.
     * @param upcToFind The UPC of the InventoryItem the user wishes to find.
     * @return The index location of the InventoryItem in the array or -1 if
     * not found.
     */
    private int LinearSearch(String upcToFind)
    {
        for (int i = 0; i < allItems.length; i++)
            if (allItems[i].getUpc().equals(upcToFind))
                return i;
        return -1;
    }
    
    /**
     * The recursive portion of the merge sort algorithm that splits the array
     * into two halves. Once it has exhausted this split it then merges the
     * arrays back together.
     * @param allItems The array to split and merge.
     * @param leftIndex The left-most index in the array.
     * @param rightIndex The right-most index in the array.
     */
    private void MergeSort(InventoryItem[] allItems, int leftIndex,
            int rightIndex)
    {
        // If there is still splitting to be done, do it and then merge them
        // afterwards. This is the recursive part of the function.
        if (rightIndex > leftIndex)
        {
            int middleIndex = (leftIndex + rightIndex) / 2;
            MergeSort(allItems, leftIndex, middleIndex);
            MergeSort(allItems, middleIndex + 1, rightIndex);
            Merge(allItems, leftIndex, middleIndex, rightIndex);
        }
        // If there is no splitting to be done, let the method that called
        // this one continue to the Merge section of the method.
    }
    
    /**
     * This is the part where the comparisons begin and the array is put back
     * together in order.
     * @param allItems The array to split and merge.
     * @param leftIndex The left-most index in the array.
     * @param middleIndex The middle splitting point of the array.
     * @param rightIndex The right-most index in the array.
     */
    private void Merge(InventoryItem[] allItems, int leftIndex, int middleIndex,
            int rightIndex)
    {
        InventoryItem[] leftSide = new InventoryItem[middleIndex - leftIndex 
                + 1];
        InventoryItem[] rightSide = new InventoryItem[rightIndex - middleIndex];
        
        System.arraycopy(allItems, leftIndex, leftSide, 0, leftSide.length);
        System.arraycopy(allItems, middleIndex + 1, rightSide, 0,
                rightSide.length);
        
        // Initializing iterators for the comparisons.
        int leftSideIterator = 0, rightSideIterator = 0, 
                allItemsCurrentIndex = leftIndex;
        
        // Keep going until we exhaust one of the arrays.
        while (leftSideIterator < leftSide.length 
                && rightSideIterator < rightSide.length)
        {
            if (leftSide[leftSideIterator].getUpc()
                    .compareTo(rightSide[rightSideIterator].getUpc()) < 0)
            {
                allItems[allItemsCurrentIndex] = leftSide[leftSideIterator];
                leftSideIterator++;
            }
            else
            {
                allItems[allItemsCurrentIndex] = rightSide[rightSideIterator];
                rightSideIterator++;
            }
            allItemsCurrentIndex++;
        }
        
        // If the left side still has more, finish adding the remainder.
        while (leftSideIterator < leftSide.length)
        {
            allItems[allItemsCurrentIndex] = leftSide[leftSideIterator];
            allItemsCurrentIndex++;
            leftSideIterator++;
        }
        
        // If the right side still has more, finish adding the remainder.
        while (rightSideIterator < rightSide.length)
        {
            allItems[allItemsCurrentIndex] = rightSide[rightSideIterator];
            allItemsCurrentIndex++;
            rightSideIterator++;
        }
    }
    
    /**
     * Performs a binary search on the sorted InventoryItem array.
     * @param allItems The entire InventoryItem array.
     * @param startIndex The left-most index to search.
     * @param endIndex The right-most index to search.
     * @param upcToFind The UPC to match.
     * @return The index of the InventoryItem object in the array.
     */
    private int BinarySearch(InventoryItem[] allItems, int startIndex,
            int endIndex, String upcToFind)
    {
        if (endIndex >= startIndex)
        {
            int middleIndex = startIndex + ((endIndex - startIndex) / 2);
            if (allItems[middleIndex].getUpc().equals(upcToFind))
                return middleIndex;
            if (upcToFind.compareTo(allItems[middleIndex].getUpc()) < 0)
                return BinarySearch(allItems, startIndex, middleIndex - 1,
                        upcToFind);
            return BinarySearch(allItems, middleIndex + 1, endIndex, upcToFind);
        }
        
        return -1;
    }

    /**
     * This is the entry point of the program. It runs the SwingUtilities'
     * invokeLater method to run the JFrame class on the thread-safe
     * AWT-EventDispatch thread.
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> 
        {
            Main theGUI = new Main();
            theGUI.setVisible(true);
        });
    }

}
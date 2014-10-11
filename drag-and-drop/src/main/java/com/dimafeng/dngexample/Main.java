package com.dimafeng.dngexample;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSource;
import java.io.IOException;


public class Main extends JFrame {

    public Main() {
        JPanel panel = new JPanel();

        DragSource ds = new DragSource();
        JButton test = new JButton("Draggable button");
        ds.createDefaultDragGestureRecognizer(
                test,
                DnDConstants.ACTION_COPY_OR_MOVE,
                (DragGestureEvent event) -> {
                    event.startDrag(DragSource.DefaultLinkDrop, new TransferData());
                });

        panel.add(test);
        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    private static class TransferData implements Transferable {

        private Gson gson = new Gson();

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{
                    new DataFlavor(String.class, null)
            };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return true;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            return gson.toJson(Data.createRandomData());
        }
    }
}
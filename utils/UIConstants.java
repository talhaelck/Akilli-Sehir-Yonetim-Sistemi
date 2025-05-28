package com.mycompany.sehir.utils;

import java.awt.Color;
import java.awt.Font;

public class UIConstants {
    // Renkler
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    public static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    public static final Color BACKGROUND_START = new Color(66, 139, 202);
    public static final Color BACKGROUND_END = new Color(47, 95, 145);
    
    // Gradient Renkler
    public static final Color[] GRADIENT_COLORS = {
        new Color(66, 139, 202),
        new Color(47, 95, 145)
    };
    
    // Fontlar
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    // Padding ve Margin
    public static final int PADDING = 20;
    public static final int MARGIN = 10;
} 
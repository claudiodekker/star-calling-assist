/*
 * Copyright (c) 2021, Cyborger1, Psikoi <https://github.com/Psikoi>, Andmcadams <https://github.com/andmcadams> (Basis)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.starcallingassist.sidepanel;

	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
	import java.awt.image.BufferedImage;
	import javax.swing.BorderFactory;
	import javax.swing.ImageIcon;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.border.CompoundBorder;
	import javax.swing.border.EmptyBorder;

	import com.starcallingassist.StarCallingAssistPlugin;
	import net.runelite.client.ui.ColorScheme;
	import net.runelite.client.ui.FontManager;
	import net.runelite.client.util.ImageUtil;

class TableHeader extends JPanel
{
    private static final ImageIcon ARROW_UP;
    private static final ImageIcon HIGHLIGHT_ARROW_DOWN;
    private static final ImageIcon HIGHLIGHT_ARROW_UP;

    private static final Color ARROW_COLOR = ColorScheme.LIGHT_GRAY_COLOR;
    private static final Color HIGHLIGHT_COLOR = ColorScheme.BRAND_ORANGE;

    static
    {
	final BufferedImage arrowDown = ImageUtil.loadImageResource(StarCallingAssistPlugin.class, "/arrow_down.png");
	final BufferedImage arrowUp = ImageUtil.rotateImage(arrowDown, Math.PI);
	final BufferedImage arrowUpFaded = ImageUtil.luminanceOffset(arrowUp, -80);
	ARROW_UP = new ImageIcon(arrowUpFaded);

	final BufferedImage highlightArrowDown = ImageUtil.fillImage(arrowDown, HIGHLIGHT_COLOR);
	final BufferedImage highlightArrowUp = ImageUtil.fillImage(arrowUp, HIGHLIGHT_COLOR);
	HIGHLIGHT_ARROW_DOWN = new ImageIcon(highlightArrowDown);
	HIGHLIGHT_ARROW_UP = new ImageIcon(highlightArrowUp);
    }

    private final JLabel textLabel = new JLabel();
    private final JLabel arrowLabel = new JLabel();
    // Determines if this header column is being used to order the list
    private boolean ordering = false;

    // Sortable
    TableHeader(String title, boolean ordered, boolean ascending)
    {
	setLayout(new BorderLayout(0, 0));
	setBackground(ColorScheme.SCROLL_TRACK_COLOR);
	setBorder(new CompoundBorder(
		BorderFactory.createMatteBorder(0, 0, 0, 1, ColorScheme.MEDIUM_GRAY_COLOR),
		new EmptyBorder(0, 2, 0, -2)));

	arrowLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
	textLabel.setBorder(new EmptyBorder(0, 0, 0, 0));

	addMouseListener(new MouseAdapter()
	{
	    @Override
	    public void mouseEntered(MouseEvent mouseEvent)
	    {
		textLabel.setForeground(HIGHLIGHT_COLOR);
		if (!ordering)
		{
		    arrowLabel.setIcon(HIGHLIGHT_ARROW_UP);
		}
	    }

	    @Override
	    public void mouseExited(MouseEvent mouseEvent)
	    {
		if (!ordering)
		{
		    textLabel.setForeground(ARROW_COLOR);
		    arrowLabel.setIcon(ARROW_UP);
		}
	    }
	});

	textLabel.setText(title);
	textLabel.setFont(FontManager.getRunescapeSmallFont());

	highlight(ordered, ascending);

	add(textLabel, BorderLayout.WEST);
	add(arrowLabel, BorderLayout.EAST);
    }

    // Non-sortable
    TableHeader(String title)
    {
	setLayout(new BorderLayout(0, 0));
	setBackground(ColorScheme.SCROLL_TRACK_COLOR);
	setBorder(new CompoundBorder(
		BorderFactory.createMatteBorder(0, 0, 0, 1, ColorScheme.MEDIUM_GRAY_COLOR),
		new EmptyBorder(0, 2, 0, -2)));

	textLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
	textLabel.setText(title);
	textLabel.setFont(FontManager.getRunescapeSmallFont());

	add(textLabel, BorderLayout.WEST);
    }

    /**
     * The labels inherit the parent's mouse listeners.
     */
    @Override
    public void addMouseListener(MouseListener mouseListener)
    {
	super.addMouseListener(mouseListener);
	textLabel.addMouseListener(mouseListener);
	arrowLabel.addMouseListener(mouseListener);
    }

    /**
     * If this column header is being used to order, then it should be
     * highlighted, changing it's font color and icon.
     */
    public void highlight(boolean on, boolean ascending)
    {
	ordering = on;
	arrowLabel.setIcon(on ? (ascending ? HIGHLIGHT_ARROW_DOWN : HIGHLIGHT_ARROW_UP) : ARROW_UP);
	textLabel.setForeground(on ? HIGHLIGHT_COLOR : ARROW_COLOR);
    }

}

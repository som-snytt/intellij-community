package com.intellij.codeInsight.lookup;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author peter
 */
public class RealLookupElementPresentation extends LookupElementPresentation {
  private final int myMaximumWidth;
  private final FontMetrics myNormalMetrics;
  private final FontMetrics myBoldMetrics;

  public RealLookupElementPresentation(int maximumWidth, FontMetrics normalMetrics, FontMetrics boldMetrics) {
    myMaximumWidth = maximumWidth;
    myNormalMetrics = normalMetrics;
    myBoldMetrics = boldMetrics;
  }

  @Override
  public boolean isReal() {
    return true;
  }

  public boolean hasEnoughSpaceFor(@Nullable String text, boolean bold) {
    return myMaximumWidth >= calculateWidth(this, myNormalMetrics, myBoldMetrics) + getStringWidth(text, bold ? myBoldMetrics : myNormalMetrics);
  }

  public static int calculateWidth(LookupElementPresentation presentation, FontMetrics normalMetrics, FontMetrics boldMetrics) {
    int result = 0;
    result += getStringWidth(presentation.getItemText(), presentation.isItemTextBold() ? boldMetrics : normalMetrics);
    result += getStringWidth(presentation.getTailText(), normalMetrics);
    final String typeText = presentation.getTypeText();
    if (StringUtil.isNotEmpty(typeText)) {
      result += getStringWidth("XXX", normalMetrics); //3 spaces for nice tail-type separation
      result += getStringWidth(typeText, normalMetrics);
    }
    result += getStringWidth("W", boldMetrics); //for unforeseen Swing size adjustments
    final Icon typeIcon = presentation.getTypeIcon();
    if (typeIcon != null) {
      result += typeIcon.getIconWidth();
    }
    return result;
  }

  public static int getStringWidth(@Nullable final String text, FontMetrics metrics) {
    if (text != null) {
      return metrics.stringWidth(text);
    }
    return 0;
  }
}

package com.cwenhui.widget.dragBubbleView;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 作者: GIndoc
 * 日期: 2017/3/2 16:47
 * 作用: PointF动画估值器
 */

public class PointFEvaluator implements TypeEvaluator<PointF> {
    @Override
    public PointF evaluate(float fraction, PointF startPointF, PointF endPointF) {
        float x = startPointF.x + fraction * (endPointF.x - startPointF.x);
        float y = startPointF.y + fraction * (endPointF.y - startPointF.y);
        return new PointF(x, y);
    }

}

package myapp.shapes;

import java.awt.BasicStroke;
import java.io.*;

public class SerializableBasicStroke implements Serializable {
    float width;
    int cap;
    int join;
    float miterlimit;
    float[] dash;
    float dash_phase;

    // 初期化用
    public SerializableBasicStroke() {
        this.width = 1.0f;
        this.cap = BasicStroke.CAP_BUTT;
        this.join = BasicStroke.JOIN_MITER;
        this.miterlimit = 10.0f;
        this.dash = null;
        this.dash_phase = 0.0f;
    }

    public SerializableBasicStroke(BasicStroke stroke) {
        this.width = stroke.getLineWidth();
        this.cap = stroke.getEndCap();
        this.join = stroke.getLineJoin();
        this.miterlimit = stroke.getMiterLimit();
        this.dash = stroke.getDashArray();
        this.dash_phase = stroke.getDashPhase();
    }

    public BasicStroke toBasicStroke() {
        return new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
    }
}

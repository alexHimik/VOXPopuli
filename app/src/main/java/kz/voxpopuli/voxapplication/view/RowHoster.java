package kz.voxpopuli.voxapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.model.rows.IRowItemModel;

/**
 * Created by user on 03.04.15.
 */
public class RowHoster extends LinearLayout {

    public static final int HEADR_VIEW_ID = 123;

    private int headerResourceID;
    private int rowResourceId;

    private Context context;
    private LayoutInflater inflater;
    private View header;

    private List<IRowItemModel> itemsForRows;
    private Map<Integer, View> hosterViews;

    public RowHoster(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
    }

    public RowHoster(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
    }

    public RowHoster(Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
    }

    public RowHoster initHoster(int headerResourceID, int rowResourceId) {
        this.headerResourceID = headerResourceID;
        this.rowResourceId = rowResourceId;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        header = inflater.inflate(headerResourceID, null);
        LinearLayout.LayoutParams headerParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        header.setId(HEADR_VIEW_ID * 10);
        addView(header, headerParams);
        return this;
    }

    public void setRowItemModels(List<IRowItemModel> models) {
        itemsForRows = models;
        hosterViews = new WeakHashMap<>();

        View row = null;
        boolean fillingRow = false;
        for(IRowItemModel model : models) {
            if(!fillingRow) {
                row = inflater.inflate(rowResourceId, null);
                View v = row.findViewById(R.id.hoster_row_left_item);
                View leftCell = model.initModelsViews(inflater);
                leftCell.setLayoutParams(v.getLayoutParams());
                leftCell.setId(HEADR_VIEW_ID * model.getResourceId());
                ((ViewGroup)row).removeView(v);
                ((ViewGroup)row).addView(leftCell, 0);
                model.setModelDataToViews(context);

                hosterViews.put(leftCell.getId(), leftCell);

                fillingRow = true;
            } else {
                View v = row.findViewById(R.id.hoster_row_right_item);
                View rightCell = model.initModelsViews(inflater);
                rightCell.setLayoutParams(v.getLayoutParams());
                rightCell.setId(HEADR_VIEW_ID * model.getResourceId());
                ((ViewGroup)row).removeView(v);
                ((ViewGroup)row).addView(rightCell, 1);
                model.setModelDataToViews(context);
                fillingRow = false;

                hosterViews.put(rightCell.getId(), rightCell);

                LinearLayout.LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
                addView(row, rowParams);
            }
        }
    }

    public List<IRowItemModel> getRowItemModels() {
        return itemsForRows;
    }
}
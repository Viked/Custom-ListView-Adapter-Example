package viked.customlistviewadapterexample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import static viked.customlistviewadapterexample.FileNavigator.getFoldersCount;

public class FileAdapter extends ArrayAdapter<File> {


    private final Drawable folderIcon48dp
            = ContextCompat.getDrawable(getContext(), R.drawable.ic_folder_black_48dp);
    private final Drawable openFolderIcon48dp
            = ContextCompat.getDrawable(getContext(), R.drawable.ic_folder_outline_black_48dp);
    private final Drawable fileIcon48dp
            = ContextCompat.getDrawable(getContext(), R.drawable.ic_file_black_48dp);
    private final Drawable txtFileIcon48dp
            = ContextCompat.getDrawable(getContext(), R.drawable.ic_file_document_black_48dp);


    public interface Callback{
        boolean isFolderOpen(File file);
    }

    Callback callback;

    public FileAdapter(Context context, Callback callback, List<File> files) {
        super(context, R.layout.file_list_row, files);
        this.callback = callback;
    }

    public static class ViewHolder {
        public TextView nameTextView;
        public TextView folderCountTextView;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            nameTextView = (TextView) itemLayoutView.findViewById(R.id.pref_name);
            folderCountTextView = (TextView) itemLayoutView.findViewById(R.id.pref_folder_count);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.pref_image_view);
        }
    }

    private void configureViewHolder(ViewHolder viewHolder, int position) {
        File file = getItem(position);
        String fileName = file.getName();
        viewHolder.nameTextView.setText(fileName);
        if(file.isDirectory()){
            if(callback.isFolderOpen(file)){
                viewHolder.imgViewIcon.setImageDrawable(openFolderIcon48dp);
            }else{
                viewHolder.imgViewIcon.setImageDrawable(folderIcon48dp);
            }
            viewHolder.folderCountTextView.setVisibility(View.VISIBLE);
            viewHolder.folderCountTextView.setText( getContext().getResources().getString(
                    R.string.folders_count) +
                    getFoldersCount(file));
        }else{
            viewHolder.folderCountTextView.setVisibility(View.GONE);
            switch (fileName.substring(fileName.lastIndexOf("."))){
                case ".txt":
                    viewHolder.imgViewIcon.setImageDrawable(txtFileIcon48dp);
                    break;
                default:
                    viewHolder.imgViewIcon.setImageDrawable(fileIcon48dp);
                    break;
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if( convertView == null ){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.file_list_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        configureViewHolder(holder, position);
        return convertView;
    }


}

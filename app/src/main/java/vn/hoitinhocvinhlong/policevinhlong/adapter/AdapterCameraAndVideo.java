package vn.hoitinhocvinhlong.policevinhlong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;

/**
 * Created by Long on 9/19/2017.
 */

public class AdapterCameraAndVideo extends RecyclerView.Adapter<AdapterCameraAndVideo.CameraAndVideoViewHolder>{

    private Context mContext;
    private List<File> mFileList;
    private ProtocolRemoveTinNhan mProtocolRemoveTinNhan;

    public AdapterCameraAndVideo(Context context, List<File> fileList, ProtocolRemoveTinNhan protocolRemoveTinNhan) {
        mContext = context;
        mFileList = fileList;
        mProtocolRemoveTinNhan = protocolRemoveTinNhan;
    }


    @Override
    public CameraAndVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_camera_and_video, parent, false);
        return new CameraAndVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CameraAndVideoViewHolder holder, final int position) {
        File file = mFileList.get(position);
        holder.bind(file);
        holder.mImageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProtocolRemoveTinNhan.complete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    public class CameraAndVideoViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageViewThumbnail, mImageViewClose;

        public CameraAndVideoViewHolder(View itemView) {
            super(itemView);
            mImageViewClose = (ImageView) itemView.findViewById(R.id.imageClose);
            mImageViewThumbnail = (ImageView) itemView.findViewById(R.id.imageThumbnail);
        }

        public void bind(File file){
            if(file.getPath().split("\\.")[1].equals("mp4")){
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(file.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                mImageViewThumbnail.setImageBitmap(thumb);
            }else{
                Picasso.with(mContext).load(file).fit().into(mImageViewThumbnail);
            }
        }
    }

    public interface ProtocolRemoveTinNhan{
        void complete(int position);
    }
}

package vn.hoitinhocvinhlong.policevinhlong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;

/**
 * Created by Long on 9/25/2017.
 */

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.ViewHolderVideo>{

    private Context mContext;
    private List<String> mVideoList;

    public AdapterVideo(Context context, List<String> videoList) {
        mContext = context;
        mVideoList = videoList;
    }

    @Override
    public ViewHolderVideo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new ViewHolderVideo(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderVideo holder, int position) {
        String url = mVideoList.get(position);
        holder.bind(url);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class ViewHolderVideo extends RecyclerView.ViewHolder{

        private VideoView mVideoView;

        public ViewHolderVideo(View itemView) {
            super(itemView);
            mVideoView = (VideoView) itemView.findViewById(R.id.videoView);
        }

        public void bind(String url){
            MediaController mediaController = new MediaController(mContext);
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            mVideoView.setVideoPath(url.split("\\.mp4")[0]);
        }
    }

}

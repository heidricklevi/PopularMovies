import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Levi on 12/28/2015.
 */
public class TopRatedFragment extends Fragment {

    String topRatedURL = "http://api.themoviedb.org/3/movie/top_rated?api_key=13ebc35e0c6a99a673ac605b5e7f3710";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

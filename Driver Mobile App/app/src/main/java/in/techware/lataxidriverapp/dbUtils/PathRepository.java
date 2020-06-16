package in.techware.lataxidriverapp.dbUtils;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.dbUtils.dao.PathDao;
import in.techware.lataxidriverapp.dbUtils.entity.PathEntity;
import in.techware.lataxidriverapp.model.PathBean;

/**
 * Created by Jemsheer K D on 14 February, 2018.
 * Package in.techware.lataxidriver.dbUtils
 * Project Dearest
 */
public class PathRepository {

    private static PathRepository instance;


    public static PathRepository getInstance() {
        if (instance == null) {
            synchronized (LaTaxiRoomDatabase.class) {
                if (instance == null) {
                    instance = new PathRepository();
                }
            }
        }
        return instance;
    }

    public void addPath(PathBean pathBean, PathRepositoryListener listener) {
        InsertPathTask insertPathTask = new InsertPathTask(pathBean, listener);
        insertPathTask.execute();

    }

    public void updatePath(PathBean pathBean, PathRepositoryListener listener) {
        UpdatePathTask updatePathTask = new UpdatePathTask(pathBean, listener);
        updatePathTask.execute();

    }

    public void getAllPath(String tripID, PathRepositoryListener listener) {
        GetAllPathTask getAllPathTask = new GetAllPathTask(tripID, listener);
        getAllPathTask.execute();

    }

    private static class InsertPathTask extends AsyncTask<String, Integer, Boolean> {
        private final PathBean pathBean;
        private final PathRepositoryListener listener;

        public InsertPathTask(PathBean pathBean, PathRepositoryListener listener) {
            super();
            this.pathBean = pathBean;
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            System.out.println(">>>>>>>>>doInBackground");
            PathDao pathDao = LaTaxiRoomDatabase.getDatabase(App.getInstance()).pathDao();
            PathEntity pathEntity = new PathEntity(pathBean);
            pathDao.insert(pathEntity);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result)
                listener.onAddCompleted();
            else
                listener.onFailed();
        }
    }

    private static class UpdatePathTask extends AsyncTask<String, Integer, Boolean> {
        private final PathBean pathBean;
        private final PathRepositoryListener listener;

        public UpdatePathTask(PathBean pathBean, PathRepositoryListener listener) {
            super();
            this.pathBean = pathBean;
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            System.out.println(">>>>>>>>>doInBackground");
            PathDao pathDao = LaTaxiRoomDatabase.getDatabase(App.getInstance()).pathDao();
            PathEntity pathEntity = new PathEntity(pathBean);
            pathDao.update(pathEntity);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result)
                listener.onUpdateCompleted();
            else
                listener.onFailed();
        }
    }


    private static class GetAllPathTask extends AsyncTask<String, Integer, ArrayList<PathBean>> {
        private final String tripID;
        private final PathRepositoryListener listener;

        public GetAllPathTask(String tripID, PathRepositoryListener listener) {
            super();
            this.tripID = tripID;
            this.listener = listener;
        }

        @Override
        protected ArrayList<PathBean> doInBackground(String... params) {
            System.out.println(">>>>>>>>>doInBackground");
            PathDao pathDao = LaTaxiRoomDatabase.getDatabase(App.getInstance()).pathDao();
            List<PathEntity> pathEntities = pathDao.getAllPath(tripID);
            ArrayList<PathBean> pathList = new ArrayList<>();
            for (PathEntity pathEntity : pathEntities) {
                PathBean pathBean = pathEntity.getBean();
                pathList.add(pathBean);
            }
            return pathList;
        }

        @Override
        protected void onPostExecute(ArrayList<PathBean> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty())
                listener.onPathListBeanLoaded(result);
            else
                listener.onFailed();
        }
    }


    public static interface PathRepositoryListener {

        void onAddCompleted();

        void onDeleteCompleted();

        void onUpdateCompleted();

        void onPathBeanLoaded(PathBean pathBean);

        void onPathListBeanLoaded(ArrayList<PathBean> pathList);

        void onFailed();

    }

}

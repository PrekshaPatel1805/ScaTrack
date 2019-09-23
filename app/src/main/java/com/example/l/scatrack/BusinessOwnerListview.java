package com.example.l.scatrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BusinessOwnerListview extends AppCompatActivity {
    ListView list;
    ArrayList<String> name,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_owner_listview);
        list = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("list")) {
                bid = intent.getStringArrayListExtra("bid");
                name = intent.getStringArrayListExtra("list");
            }
        }
        MyBaseAdapter adapter1 = new MyBaseAdapter(this, name);
        list.setAdapter(adapter1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(BusinessOwnerListview.this,BusinessInfo.class);
                i.putExtra("bid",bid.get(pos));
                startActivity(i);
            }
        });
    }

    public class MyBaseAdapter extends BaseAdapter {

        ArrayList myList = new ArrayList();
        LayoutInflater inflater;
        Context context;


        public MyBaseAdapter(Context context, ArrayList myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Object getItem(int i) {
            return myList.get(i);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.rowlistview, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);

            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }


            mViewHolder.user1.setText(myList.get(position).toString());

            return convertView;
        }

        private class MyViewHolder {
            TextView user1;
            ImageView iv_image;

            public MyViewHolder(View item) {
                user1 = (TextView) item.findViewById(R.id.user1);
                iv_image = (ImageView) item.findViewById(R.id.iv_image);
            }
        }
    }
}


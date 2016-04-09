
# WrapRecyclerView

**[English](https://github.com/xuehuayous/WrapRecyclerView)** **[中文](https://github.com/xuehuayous/WrapRecyclerView/blob/master/README-zh.md)**

WrapRecyclerView is a RecyclerView that can be add header and footer. And provide a WrapAdapter, it allows you to easily add headers and footers for RecyclerView.

![Screenshot](https://raw.githubusercontent.com/xuehuayous/WrapRecyclerView/master/sample/sample.gif)

In the example, The Android-LoopView is used as rotation picture, you can use it to easily achieve the rotation picture.

## Using [WrapRecyclerView](https://github.com/xuehuayous/WrapRecyclerView) in your application

If you are building with Gradle, simply add the following line to the `dependencies` section of your `build.gradle` file:

```
	compile 'com.kevin:wraprecyclerview:1.0.5'
```

# How to use

Provides two sets of implementation of the program, you can choose according to their own needs. First, the direct use of **WrapRecyclerView**, which is an enhanced version of the RecyclerView, provides a method to add the header and footer; the other is to use **WrapAdapter**, which is set to RecyclerView.Adapter as a wrapper class to RecyclerView.

### WrapRecyclerView

- As an enhancement of RecyclerView, you can use it as a RecyclerView

        // Create RecyclerView data adapter
		mAdapter = new PictureAdapter(this);
		// Set RecyclerView data adapter
        mWrapRecyclerView.setAdapter(mAdapter);

- You can get the **wrapper adapter** by **WrapRecyclerView**

        // Gets the wrapper adapter because you should to use it to refresh the data
        WrapAdapter mWrapAdapter = mWrapRecyclerView.getAdapter();

- Change data by **real data adapter** 

		// real data adapter sets the data
        mAdapter.setItemLists(pictureData.pictures);

- Provides an **BaseRecyclerAdapter** to facilitate the operation of your adapter data

### WrapAdapter

- WrapAdapter is used as a RecyclerView.Adapter wrapper class to add the header and footer to the RecyclerView.

		// Create RecyclerView data adapter
		PictureAdapter pictureAdapter = new PictureAdapter(this);
		// Create adapter wrapper
		mWrapAdapter = new WrapAdapter<>(pictureAdapter);
		// set the header and footer to be added as a row
		mWrapAdapter.adjustSpanSize(mRecyclerView);
		// Set RecyclerView data adapter (adapter wrapper)
		mRecyclerView.setAdapter(mWrapAdapter);

- Add header footer layout

		mWrapAdapter.addHeaderView(headerView);
		mWrapAdapter.addFooterView(footerView);

- You can get the real data adapter by **wrapper adapter**.

		PictureAdapter pictureAdapter = mWrapAdapter.getWrappedAdapter();

- Change data by **real data adapter** and **wrapper adapter** notification data change

		// Gets the real data adapter and sets the data
		mWrapAdapter.getWrappedAdapter().setItemLists(pictureData.pictures);
		// Wrapper adapter notification data change
		mWrapAdapter.notifyDataSetChanged();

## License

    Copyright 2015, Kevin.zhou

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


# WrapRecyclerView

**[English](https://github.com/xuehuayous/WrapRecyclerView)** **[中文](https://github.com/xuehuayous/WrapRecyclerView/blob/master/README-zh.md)**

WrapRecyclerView 是一个可以添加头部和尾部的RecyclerView，并且提供了一个 WrapAdapter, 它可以让你轻松为 RecyclerView 添加头部和尾部。

![Screenshot](https://raw.githubusercontent.com/xuehuayous/WrapRecyclerView/master/sample/sample.gif)

示例中轮转图使用了**[Android-LoopView](https://github.com/xuehuayous/Android-LoopView)**,使用它你可以轻松实现轮转大图。

## 在项目中使用[WrapRecyclerView](https://github.com/xuehuayous/WrapRecyclerView) 

如果您的项目使用 Gradle 构建, 只需要在您的`build.gradle`文件添加下面一行到 `dependencies` :

```
	compile 'com.kevin:wraprecyclerview:1.0.5'
```

## 使用说明

提供了两套实现的方案，您可以根据自己的需要来选择。一是直接使用**WrapRecyclerView**，它是一个增强版的RecyclerView，提供了添加头部和尾部的方法；另一种是使用**WrapAdapter**，把它作为RecyclerView.Adapter的包装类设置给RecyclerView。

### WrapRecyclerView

- 作为RecyclerView的增强，你可以把它作为RecyclerView来使用

        // 创建数据适配器
		mAdapter = new PictureAdapter(this);
		// 设置WrapRecyclerView数据适配器
        mWrapRecyclerView.setAdapter(mAdapter);

- 可以通过**WrapRecyclerView**来获取适配器包装

        // 获取包装类适配器，因为要用它去刷新数据
        WrapAdapter mWrapAdapter = mWrapRecyclerView.getAdapter();

- 通过 **真实适配器** 更改数据

		// 数据适配器设置数据
        mAdapter.setItemLists(pictureData.pictures);

- 还提供了一个 **BaseRecyclerAdapter** 来方便您对适配器数据的操作

### WrapAdapter

- WrapAdapter是作为RecyclerView.Adapter的包装类来使用的，在包装类中完成的添加头部和尾部

		// 创建RecyclerView的数据适配器
		PictureAdapter pictureAdapter = new PictureAdapter(this);
		// 创建适配器包装
		mWrapAdapter = new WrapAdapter<>(pictureAdapter);
		// 设置头部和尾部占据一行
		mWrapAdapter.adjustSpanSize(mRecyclerView);
		// 设置RecyclerView的数据适配器(适配器包装)
		mRecyclerView.setAdapter(mWrapAdapter);

- 添加头部、尾部布局
		mWrapAdapter.addHeaderView(headerView);
		mWrapAdapter.addFooterView(footerView);

- 可以通过 **适配器包装** 来获取真实的数据适配器

		PictureAdapter pictureAdapter = mWrapAdapter.getWrappedAdapter();

- 通过 **真实适配器** 更改数据并且使用 **适配器包装** 通知数据变更

		// 获取真实数据适配器并设置数据
		mWrapAdapter.getWrappedAdapter().setItemLists(pictureData.pictures);
		// 包装适配器通知数据变更
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

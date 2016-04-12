package com.kevin.wraprecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权所有：XXX有限公司
 *
 * WrapAdapter
 *
 * @author zhou.wenkai ,Created on 2015-11-24 10:48:29
 * Major Function：A RecyclerView.Adapter that allows for headers and footers as well.
 *
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
@SuppressWarnings("rawtypes")
public class WrapAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final T mRealAdapter;
	private boolean isStaggeredGrid;

	// Defines available view type integers for headers and footers.
	private static final int BASE_HEADER_VIEW_TYPE = -1 << 10;
	private static final int BASE_FOOTER_VIEW_TYPE = -1 << 11;

	private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
	private ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<>();

	/**
	 * A class that represents a fixed view in a list, for example a header at the top
	 * or a footer at the bottom.
	 */
	public class FixedViewInfo {
		/** The view to add to the list */
		public View view;
		/** The data backing the view. This is returned from {RecyclerView.Adapter#getItemViewType(int)}. */
		public int viewType;
	}

	/**
	 * Constructor.
	 *
	 * @param adapter
	 * 		the adapter to wrap
	 */
	public WrapAdapter(T adapter) {
		super();
		mRealAdapter = adapter;
	}

	/**
	 * Gets the real adapter
	 *
	 * @return T:
	 */
	public T getWrappedAdapter() {
		return mRealAdapter;
	}

	/**
	 * Adds a header view
	 *
	 * @param view
	 */
	public void addHeaderView(View view) {
		if (null == view) {
			throw new IllegalArgumentException("the view to add must not be null!");
		}
		final FixedViewInfo info = new FixedViewInfo();
		info.view = view;
		info.viewType = BASE_HEADER_VIEW_TYPE + mHeaderViewInfos.size();
		mHeaderViewInfos.add(info);
		notifyDataSetChanged();
	}

	/**
	 * Adds a footer view
	 *
	 * @param view
	 */
	public void addFooterView(View view, boolean reverse) {
		if (null == view) {
			throw new IllegalArgumentException("the view to add must not be null!");
		}
		final FixedViewInfo info = new FixedViewInfo();
		info.view = view;
		info.viewType = BASE_FOOTER_VIEW_TYPE + mFooterViewInfos.size();
		mFooterViewInfos.add(info);
		if(reverse) {
			for(int i = 0; i < mFooterViewInfos.size(); i++) {
				FixedViewInfo fixedViewInfo = mFooterViewInfos.get(i);
				fixedViewInfo.viewType = BASE_FOOTER_VIEW_TYPE + mFooterViewInfos.size() - i - 1;
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * Adds a footer view
	 *
	 * @param view
	 */
	public void addFooterView(View view) {
		addFooterView(view, false);
	}

	/**
	 * gets the headers view
	 *
	 * @return List:
	 * @version 1.0
	 */
	public List<View> getHeadersView() {
		List<View> viewList = new ArrayList<View>(getHeadersCount());
		for (FixedViewInfo fixedViewInfo : mHeaderViewInfos) {
			viewList.add(fixedViewInfo.view);
		}
		return viewList;
	}

	/**
	 * gets the footers view
	 *
	 * @return List:
	 * @version 1.0
	 */
	public List<View> getFootersView() {
		List<View> viewList = new ArrayList<View>(getHeadersCount());
		for (FixedViewInfo fixedViewInfo : mFooterViewInfos) {
			viewList.add(fixedViewInfo.view);
		}
		return viewList;
	}

	/**
	 * adjust the GridLayoutManager SpanSize
	 *
	 * @param recycler
	 * @version 1.0
	 */
	public void adjustSpanSize(RecyclerView recycler) {
		if(recycler.getLayoutManager() instanceof GridLayoutManager) {
			final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
			layoutManager.setSpanSizeLookup(new SpanSizeLookup() {

				@Override
				public int getSpanSize(int position) {
					boolean isHeaderOrFooter =
							isHeaderPosition(position) || isFooterPosition(position);
					return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
				}

			});
		}

		if(recycler.getLayoutManager() instanceof StaggeredGridLayoutManager) {
			this.isStaggeredGrid = true;
		}
	}

	/**
	 * Setting the visibility of the header views
	 *
	 * @param shouldShow
	 * @version 1.0
	 */
	public void setHeaderVisibility(boolean shouldShow) {
		for (FixedViewInfo fixedViewInfo : mHeaderViewInfos) {
			fixedViewInfo.view.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
		}
		notifyDataSetChanged();
	}

	/**
	 * Setting the visibility of the footer views
	 *
	 * @param shouldShow
	 * @version 1.0
	 */
	public void setFooterVisibility(boolean shouldShow) {
		for (FixedViewInfo fixedViewInfo : mFooterViewInfos) {
			fixedViewInfo.view.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
		}
		notifyDataSetChanged();
	}

	/**
	 * get the count of headers
	 *
	 * @return number of headers
	 * @version 1.0
	 */
	public int getHeadersCount() {
		return mHeaderViewInfos.size();
	}

	/**
	 * get the count of footers
	 *
	 * @return the number of footers
	 * @version 1.0
	 */
	public int getFootersCount() {
		return mFooterViewInfos.size();
	}

	private boolean isHeader(int viewType) {
		return viewType >= BASE_HEADER_VIEW_TYPE
				&& viewType < (BASE_HEADER_VIEW_TYPE + mHeaderViewInfos.size());
	}

	private boolean isFooter(int viewType) {
		return viewType >= BASE_FOOTER_VIEW_TYPE
				&& viewType < (BASE_FOOTER_VIEW_TYPE + mFooterViewInfos.size());
	}

	private boolean isHeaderPosition(int position) {
		return position < mHeaderViewInfos.size();
	}

	private boolean isFooterPosition(int position) {
		return position >= mHeaderViewInfos.size() + mRealAdapter.getItemCount();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		if (isHeader(viewType)) {
			int whichHeader = Math.abs(viewType - BASE_HEADER_VIEW_TYPE);
			View headerView = mHeaderViewInfos.get(whichHeader).view;
			return createHeaderFooterViewHolder(headerView);

		} else if (isFooter(viewType)) {
			int whichFooter = Math.abs(viewType - BASE_FOOTER_VIEW_TYPE);
			View footerView = mFooterViewInfos.get(whichFooter).view;
			return createHeaderFooterViewHolder(footerView);

		} else {
			return mRealAdapter.onCreateViewHolder(viewGroup, viewType);
		}
	}

	private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
		if (isStaggeredGrid) {
			StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(
					StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
			params.setFullSpan(true);
			view.setLayoutParams(params);
		}
		return new RecyclerView.ViewHolder(view) {
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		if (position < mHeaderViewInfos.size()) {
			// Headers don't need anything special

		} else if (position < mHeaderViewInfos.size() + mRealAdapter.getItemCount()) {
			// This is a real position, not a header or footer. Bind it.
			mRealAdapter.onBindViewHolder(viewHolder, position - mHeaderViewInfos.size());

		} else {
			// Footers don't need anything special
		}
	}

	@Override
	public int getItemCount() {
		return mHeaderViewInfos.size() + mRealAdapter.getItemCount() + mFooterViewInfos.size();
	}

	@Override
	public int getItemViewType(int position) {
		if (isHeaderPosition(position)) {
			return mHeaderViewInfos.get(position).viewType;

		} else if (isFooterPosition(position)) {
			return mFooterViewInfos.get(position - mHeaderViewInfos.size()
					- mRealAdapter.getItemCount()).viewType;

		} else {
			return mRealAdapter.getItemViewType(position - mHeaderViewInfos.size());
		}
	}
}
package com.victorlh.android.framework.listas.expandibles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.victorlh.android.framework.listas.IEventosListas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Victor
 * 09/11/2018
 */
public abstract class AbstractListaExpandible<IGL extends ItemGroupLista<ICL>, ICL extends ItemChildLista, GVH extends AbstractViewHolderGroup<IGL, ICL>, CVH extends AbstractViewHolderChild<ICL>> extends AbstractExpandableItemAdapter<GVH, CVH> {

	private int lyGroupId;
	private int lyChildId;

	private List<IGL> lista;

	private IEventosListas.OnDataListChangeListener onDataListChangeListener;

	public AbstractListaExpandible(int lyGroupId, int lyChildId) {
		setHasStableIds(true);
		lista = new ArrayList<>();
		this.lyGroupId = lyGroupId;
		this.lyChildId = lyChildId;
	}

	@Override
	public int getGroupCount() {
		return lista.size();
	}

	@Override
	public int getChildCount(int groupPosition) {
		IGL itemGroup = getItemGroup(groupPosition);
		if (itemGroup == null) {
			return 0;
		}
		ICL[] childs = itemGroup.getChilds();
		return childs == null ? 0 : childs.length;
	}

	public IGL getItemGroup(int groupPosition) {
		if (groupPosition < 0 || groupPosition >= getGroupCount()) {
			return null;
		}
		return lista.get(groupPosition);
	}

	public ICL getItemChild(int groupPosition, int childPosition) {
		IGL itemGroup = getItemGroup(groupPosition);
		if (itemGroup == null) {
			return null;
		}
		ICL[] childs = itemGroup.getChilds();
		if (childs == null || childPosition < 0 || childPosition >= childs.length) {
			return null;
		}
		return childs[childPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		IGL itemGroup = getItemGroup(groupPosition);
		return itemGroup == null ? 0 : itemGroup.getId();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		ICL itemChild = getItemChild(groupPosition, childPosition);
		return itemChild == null ? 0 : itemChild.getId();
	}

	@Override
	@NonNull
	public GVH onCreateGroupViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(lyGroupId, parent, false);
		return createGroupViewHolder(v);
	}

	protected abstract GVH createGroupViewHolder(View v);

	@Override
	@NonNull
	public CVH onCreateChildViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(lyChildId, parent, false);
		return createChildViewHolder(v);
	}

	protected abstract CVH createChildViewHolder(View v);

	@Override
	public void onBindGroupViewHolder(@NonNull GVH holder, int groupPosition, int viewType) {
		IGL itemGroup = getItemGroup(groupPosition);
		holder.procesar(itemGroup, groupPosition);
	}

	@Override
	public void onBindChildViewHolder(@NonNull CVH holder, int groupPosition, int childPosition, int viewType) {
		ICL itemChild = getItemChild(groupPosition, childPosition);
		holder.procesar(itemChild, groupPosition, childPosition);
	}

	@Override
	public boolean onCheckCanExpandOrCollapseGroup(@NonNull GVH holder, int groupPosition, int x, int y, boolean expand) {
		return true;
	}

	public IGL getItemGroupById(long id) {
		for (IGL item : lista) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	public int getItemPosition(IGL item) {
		return getItemPosition(item, false);
	}

	public int getItemPosition(IGL item, boolean byId) {
		int i = lista.indexOf(item);
		if (i == -1 && byId) {
			long id = item.getId();
			IGL aux = getItemGroupById(id);
			if (aux != null) {
				i = lista.indexOf(aux);
			}
		}
		return i;
	}

	public List<IGL> getLista() {
		return new ArrayList<>(lista);
	}

	public void setLista(IGL[] lista) {
		List<IGL> list = Arrays.asList(lista);
		setLista(list);
	}

	public void setLista(List<IGL> list) {
		boolean empty = this.lista.isEmpty();
		this.lista.clear();
		this.lista.addAll(list);
		if (onDataListChangeListener != null) {
			if (!empty && lista.isEmpty()) {
				onDataListChangeListener.onVoidData();
			} else if (empty && !lista.isEmpty()) {
				onDataListChangeListener.onSetData();
			} else {
				onDataListChangeListener.onChangeData();
			}
		}
		notifyDataSetChanged();
	}

	public void addItem(@NonNull IGL item) {
		addItem(item, this.lista.size());
	}

	public void addItem(@NonNull IGL item, int position) {
		boolean empty = this.lista.isEmpty();
		if (position == lista.size()) {
			lista.add(item);
		} else {
			lista.add(position, item);
		}
		notifyDataSetChanged();
		if (onDataListChangeListener != null) {
			if (empty) {
				onDataListChangeListener.onSetData();
			} else {
				onDataListChangeListener.onChangeData();
			}
		}
	}

	public void setItem(@NonNull IGL item) {
		int itemPosition = getItemPosition(item, true);
		if (itemPosition == -1) {
			addItem(item);
		} else {
			lista.set(itemPosition, item);
			notifyDataSetChanged();
			if (onDataListChangeListener != null) {
				onDataListChangeListener.onChangeData();
			}
		}
	}

	public void deleteItem(@NonNull IGL item) {
		int itemPosition = getItemPosition(item, true);
		if (itemPosition != -1) {
			lista.remove(itemPosition);
			notifyDataSetChanged();
			if (onDataListChangeListener != null && lista.isEmpty()) {
				onDataListChangeListener.onVoidData();
			}
		}
	}

	public void setOnDataListChangeListener(IEventosListas.OnDataListChangeListener onDataListChangeListener) {
		this.onDataListChangeListener = onDataListChangeListener;
	}

	public void recargarLista() {
		notifyDataSetChanged();
	}

}

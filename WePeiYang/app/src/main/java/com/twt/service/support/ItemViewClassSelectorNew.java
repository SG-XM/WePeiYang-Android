package com.twt.service.support;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * An {@link ItemViewSelector} that selects an item view based on the class of the given item.
 * <pre>{@code
 * selector = ItemViewClassSelector.builder()
 *     .put(String.class, BR.name, R.layout.item_name)
 *     .put(Footer.class, ItemView.BINDING_VARIABLE_NONE, R.layout.item_footer)
 *     .build();
 * }</pre>
 */
public class ItemViewClassSelectorNew<T> implements OnItemBind<T>{

    /**
     * Returns a new builder to construct an {@code ItemViewClassSelector} instance.
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    private final SimpleArrayMap<Class<? extends T>, ItemBinding> ItemBindingMap;

    ItemViewClassSelectorNew(SimpleArrayMap<Class<? extends T>, ItemBinding> ItemBindingMap) {
        this.ItemBindingMap = ItemBindingMap;
    }


//    @Override
    public int viewTypeCount() {
        return ItemBindingMap.size();
    }

    @Override
    public void onItemBind(ItemBinding itemBinding, int position, T item) {
        ItemBinding itemBinding1 = ItemBindingMap.get(item.getClass());
        if (itemBinding1 != null) {
            itemBinding.set(itemBinding1.variableId(), itemBinding1.layoutRes());
        } else {
            throw new IllegalArgumentException("Missing class for item " + item);
        }
    }

    public static class Builder<T> {
        private final SimpleArrayMap<Class<? extends T>, ItemBinding> itemViewMap = new SimpleArrayMap<>();

        Builder() {
        }

        public Builder<T> put(@NonNull Class<? extends T> itemClass, int bindingVariable, @LayoutRes int layoutRes) {
            itemViewMap.put(itemClass, ItemBinding.of(bindingVariable, layoutRes));
            return this;
        }

        public Builder<T> put(@NonNull Class<? extends T> itemClass, @NonNull ItemBinding itemBinding) {
            itemViewMap.put(itemClass,itemBinding );
            return this;
        }

        public ItemViewClassSelectorNew<T> build() {
            return new ItemViewClassSelectorNew<>(itemViewMap);
        }
    }
}

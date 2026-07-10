package com.metropolitan.prodavnica_pz.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.prodavnica_pz.databinding.ShopRowBinding;
import com.metropolitan.prodavnica_pz.models.Product;

public class ProductListAdapter extends ListAdapter<Product, ProductListAdapter.ProductViewHolder> {

    ProductInterface productInterface;

    public ProductListAdapter(ProductInterface productInterface) {
        super(Product.itemCallback);
        this.productInterface = productInterface;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ShopRowBinding shopRowBinding = ShopRowBinding.inflate(layoutInflater, parent, false);
        shopRowBinding.setProductInterface(productInterface);
        return new ProductViewHolder(shopRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        holder.shopRowBinding.setProduct(product);
        holder.shopRowBinding.executePendingBindings();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ShopRowBinding shopRowBinding;

        public ProductViewHolder(ShopRowBinding binding) {
            super(binding.getRoot());
            this.shopRowBinding = binding;
        }
    }

    public interface ProductInterface {
        void addItem(Product product);

        void onItemClick(Product product);
    }

}

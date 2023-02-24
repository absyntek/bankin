package com.example.bankintest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.bankintest.databinding.ViewHolderCategoryBinding
import com.example.bankintest.models.Category

/*** Reference technique
 * @author Etienne
 * @since 24/02/2023
 * @see <a href="TODO">Spec</a>
 * @see <a href="TODO">CT</a>
 */
class CategoryAdapter : RecyclerView.Adapter<DataBindingViewHolder<ViewHolderCategoryBinding>>() {

    private var items: List<Category> = listOf()
    var onItemClick: ((Category) -> Unit)? = null

    fun updateItem(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<ViewHolderCategoryBinding> {
        val bind =
            ViewHolderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataBindingViewHolder(bind)
    }

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<ViewHolderCategoryBinding>,
        position: Int
    ) {
        val bind = holder.binding
        val item = items[position]
        bind.tvName.text = item.name
        bind.root.setOnClickListener {
            onItemClick?.let { it(item) }
        }
    }
}

open class DataBindingViewHolder<T : ViewDataBinding>(val binding: T): RecyclerView.ViewHolder(binding.root)
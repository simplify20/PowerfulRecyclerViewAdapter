#支持多种ViewHolder类型的RecyclerView.Adapter的实现
- 自定义ViewHolder，继承BaseRecyclerViewHolder
- 自定义DataBean,继承BaseDataBean
- 对于只展示静态数据或没有数据（无文字显示）的ViewHolder，使用CommonDisplayBean，默认创建BaseRecyclerViewHolder
- 复用ViewHolder和DataBean
 使用接口关联DateBean和ViewHolder
  
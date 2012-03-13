select b.typename,a.*,
(ifnull((select sum(sl.sum) from order_material_store_list sl where sl.materialid=a.id and sl.updttime>='2012-03-01' and sl.updttime<'2012-04-01'),0)+ifnull((select initsum from material_store_list where storedate='201202' and id=a.id),0)) as input,
(ifnull((select sum(ool.goodsnumber*fm.amount) from order_output_list ool,foot_material fm where fm.footid=ool.goodsid and fm.materialid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01' ),0)) as output,
(ifnull((select sum(omsl.sum) from order_material_store_list omsl where omsl.materialid=a.id and omsl.updttime>='2012-03-01' and omsl.updttime<'2012-04-01'),0)
	+ifnull((select msl.initsum from material_store_list msl where msl.storedate='201202' and msl.id=a.id),0)
	-(ifnull((select sum(ol.goodsnumber*fm.amount) from order_output_list ol,foot_material fm where fm.footid=ol.goodsid and fm.materialid=a.id and fm.issecond='0' and ol.updttime>='2012-03-01' and ol.updttime<'2012-04-01' ),0))
	-(ifnull((select sum(osml.sum) from order_second_material_list osml where osml.materialid=a.id and osml.updttime>='2012-03-01' and osml.updttime<'2012-04-01'),0))) as sum 
from material_list a, material_type b 
where a.typeid=b.id;

select b.typename,a.*,
(ifnull((select sum(sl.sum) from order_material_store_list sl where sl.materialid=a.id and sl.updttime>='2012-03-01' and sl.updttime<'2012-04-01'),0)+ifnull((select initsum from material_store_list where storedate='201202' and id=a.id),0)) as input,
(ifnull((select sum(ool.goodsnumber*fm.amount) from order_output_list ool,foot_material fm where fm.footid=ool.goodsid and fm.materialid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01' ),0)) as output,
(ifnull((select sum(omsl.sum) from order_material_store_list omsl where omsl.materialid=a.id and omsl.updttime>='2012-03-01' and omsl.updttime<'2012-04-01'),0)
	+ifnull((select msl.initsum from material_store_list msl where msl.storedate='201202' and msl.id=a.id),0)
	-(ifnull((select sum(ol.goodsnumber*fm.amount) from order_output_list ol,foot_material fm where fm.footid=ol.goodsid and fm.materialid=a.id and fm.issecond='0' and ol.updttime>='2012-03-01' and ol.updttime<'2012-04-01' ),0))
	-(ifnull((select sum(osml.sum) from order_second_material_list osml where osml.materialid=a.id and osml.updttime>='2012-03-01' and osml.updttime<'2012-04-01'),0))) as sum 
from material_store_list a, material_type b 
where a.typeid=b.id;
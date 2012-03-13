select b.typename,a.*,c.paramsname,
(ifnull((select sum(sum) from order_material_store_list where materialid=a.id),0)+ifnull((select initsum from material_store_list where storedate='201202' and id=a.id),0)) as input,
(ifnull((select sum(goodsnumber*fm.amount) from order_output_list,foot_material fm where fm.footid=goodsid and fm.materialid=a.id ),0)) as output,
(ifnull((select sum(goodsnumber) from order_output_list,foot_material fm where fm.footid=goodsid and fm.materialid=a.id ),0)) as output1,
(ifnull((select sum(fm.amount) from foot_material fm where fm.materialid=a.id ),0)) as output2,
(ifnull((select sum(sum) from order_material_store_list where materialid=a.id),0)
	+ifnull((select initsum from material_store_list where storedate='201202' and id=a.id),0)
	-(ifnull((select sum(goodsnumber*fm.amount) from order_output_list,foot_material fm where fm.footid=goodsid and fm.materialid=a.id ),0))) as sum 
from material_store_list a, material_type b,material_list c 
where a.typeid=b.id and a.id=c.id;
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

select b.typename,a.*,c.paramsname as materialname,d.paramsname as unitname,c.cost as cost,
ifnull((select sum(msl.initsum) from material_store_list msl where msl.id=a.id and msl.storedate='201202'),0) as monthinit,
(ifnull((select sum(sl.sum) from order_material_store_list sl where sl.materialid=a.id and sl.updttime>='2012-03-01' and sl.updttime<'2012-04-01'),0)) as input,
(ifnull((select sum(ool.goodsnumber*fm.amount) from order_output_list ool,foot_material fm where ool.consumetype='1' and ool.checkyn='1' and fm.footid=ool.goodsid and fm.materialid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01' and fm.issecond='0' ),0)+ifnull((select sum(osm.sum) from order_second_material_list osm where osm.materialid=a.id and osm.updttime>='2012-03-01' and osm.updttime<'2012-04-01'),0)) as output,
((ifnull((select sum(ool.goodsnumber*fm.amount) from order_output_list ool,foot_material fm where ool.consumetype='1' and ool.checkyn='1' and fm.footid=ool.goodsid and fm.materialid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01' and fm.issecond='0' ),0)+ifnull((select sum(osm.sum) from order_second_material_list osm where osm.materialid=a.id and osm.updttime>='2012-03-01' and osm.updttime<'2012-04-01'),0))*c.cost) as spend,
(ifnull((select sum(omsl.sum) from order_material_store_list omsl where omsl.materialid=a.id and omsl.updttime>='2012-03-01' and omsl.updttime<'2012-04-01'),0)
	+ifnull((select msl.initsum from material_store_list msl where msl.storedate='201202' and msl.id=a.id),0)
	-(ifnull((select sum(ol.goodsnumber*fm.amount) from order_output_list ol,foot_material fm where ol.consumetype='1' and ol.checkyn='1' and fm.footid=ol.goodsid and fm.materialid=a.id and fm.issecond='0' and ol.updttime>='2012-03-01' and ol.updttime<'2012-04-01' ),0))
	-(ifnull((select sum(osml.sum) from order_second_material_list osml where osml.materialid=a.id and osml.updttime>='2012-03-01' and osml.updttime<'2012-04-01'),0))) as sum,
(ifnull((select sum(ool.goodsnumber*fm.amount) from order_output_back_list ool,foot_material fm where ool.goodsid=fm.footid and fm.materialid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01' and fm.issecond='0'),0)+ifnull((select sum(osm.sum) from order_second_material_back_list osm where osm.materialid=a.id ),0)) as losssum,
(ifnull((select sum(ool.goodsnumber*fm.amount) from order_output_back_list ool,foot_material fm where ool.goodsid=fm.footid and fm.materialid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01' and fm.issecond='0'),0)+ifnull((select sum(osm.sum) from order_second_material_back_list osm where osm.materialid=a.id ),0))*c.cost as losscostsum 
from material_store_list a, material_type b,material_list c,params_list d   
where a.typeid=b.id and a.id=c.id and c.unit=d.paramscode and d.typeid='UNIT' and a.storedate='201203';

select a.paramsname as footname,sum(b.goodsnumber) as goodsnumber, sum(b.shouldpay) as shouldpay,sum(b.actuallypay) as actuallypay,d.paramsname as materialname,c.amount,sum(b.goodsnumber)*c.amount as allamount 
from foot_list a,order_output_list b,foot_material c,material_list d,order_output_back_list e   
where a.id=b.goodsid and b.consumetype='1' and b.checkyn='1' and b.updttime>='2012-03-01' and b.updttime<'2012-04-01' and a.id=c.footid and c.materialid=d.id 
group by d.paramsname,a.paramsname order by a.paramsname;

select a.paramsname as footname,c.paramsname as materialname,c.cost as materialcost,
ifnull((select sum(ool.goodsnumber) from order_output_list ool where ool.goodsid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01'),0) as sellamount,
ifnull((select sum(ool.actuallypay) from order_output_list ool where ool.goodsid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01'),0) as sellpay,
b.amount as materialamount,
((ifnull((select sum(ool.goodsnumber) from order_output_list ool,foot_material fm where ool.goodsid=fm.footid and fm.materialid=b.materialid and fm.issecond='0' and ool.goodsid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01'),0)*b.amount)+ifnull((select sum(osm.sum) from order_second_material_list osm where osm.materialid=b.materialid and osm.goodsid=a.id and osm.updttime>='2012-03-01' and osm.updttime<'2012-04-01'),0)) as materialsum,
((ifnull((select sum(ool.goodsnumber) from order_output_list ool,foot_material fm where ool.goodsid=fm.footid and fm.materialid=b.materialid and fm.issecond='0' and ool.goodsid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01'),0)*b.amount)+ifnull((select sum(osm.sum) from order_second_material_list osm where osm.materialid=b.materialid and osm.goodsid=a.id and osm.updttime>='2012-03-01' and osm.updttime<'2012-04-01'),0))*c.cost as materialcostsum,
((ifnull((select sum(ool.goodsnumber) from order_output_back_list ool,foot_material fm where ool.goodsid=fm.footid and fm.materialid=b.materialid and fm.issecond='0' and ool.goodsid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01'),0))+ifnull((select sum(osm.sum) from order_second_material_back_list osm where osm.materialid=b.materialid and osm.goodsid=a.id and osm.updttime>='2012-03-01' and osm.updttime<'2012-04-01'),0)) as lossamount,
((ifnull((select sum(ool.goodsnumber) from order_output_back_list ool,foot_material fm where ool.goodsid=fm.footid and fm.materialid=b.materialid and fm.issecond='0' and ool.goodsid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01'),0)*b.amount)+ifnull((select sum(osm.sum) from order_second_material_back_list osm where osm.materialid=b.materialid and osm.goodsid=a.id and osm.updttime>='2012-03-01' and osm.updttime<'2012-04-01'),0)) as lossmaterialsum,
((ifnull((select sum(ool.goodsnumber) from order_output_back_list ool,foot_material fm where ool.goodsid=fm.footid and fm.materialid=b.materialid and fm.issecond='0' and ool.goodsid=a.id and ool.updttime>='2012-03-01' and ool.updttime<'2012-04-01'),0)*b.amount)+ifnull((select sum(osm.sum) from order_second_material_back_list osm where osm.materialid=b.materialid and osm.goodsid=a.id and osm.updttime>='2012-03-01' and osm.updttime<'2012-04-01'),0))*c.cost as lossmaterialcostsum,

ifnull((select sum(msl.initsum) from material_store_list msl where msl.id=b.materialid and msl.storedate='201202'),0) as monthinit,
(ifnull((select sum(sl.sum) from order_material_store_list sl where sl.materialid=b.materialid and sl.updttime>='2012-03-01' and sl.updttime<'2012-04-01'),0)) as input,
ifnull((select sum(msl.initsum) from material_store_list msl where msl.id=b.materialid and msl.storedate='201203'),0) as monthend,
(ifnull((select sum(msl.initsum) from material_store_list msl where msl.id=b.materialid and msl.storedate='201202'),0)
	+ifnull((select sum(sl.sum) from order_material_store_list sl where sl.materialid=b.materialid and sl.updttime>='2012-03-01' and sl.updttime<'2012-04-01'),0)
	-ifnull((select sum(ol.goodsnumber*fm.amount) from order_output_list ol,foot_material fm where ol.consumetype='1' and ol.checkyn='1' and fm.footid=ol.goodsid and fm.materialid=b.materialid and fm.issecond='0' and ol.updttime>='2012-03-01' and ol.updttime<'2012-04-01' ),0)
	-ifnull((select sum(osml.sum) from order_second_material_list osml where osml.materialid=b.materialid and osml.updttime>='2012-03-01' and osml.updttime<'2012-04-01'),0)) as monthshouldend
from foot_list a,foot_material b,material_list c 
where a.id=b.footid and b.materialid=c.id
order by a.paramsname;


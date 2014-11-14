<#macro show_structure structure>
	<#if structure.children?? && (structure.children?size>0)>
		<#list structure.children?sort_by(["orderNo"]) as child>
			<@show_structure child/>
		</#list>
    <#else>
    <div class="tx-box fl" structure_id="${structure.id}">${structure.title} ${structure.description}</div>
    <#if structure.items?? && (structure.items?size>0)>
    <#assign total = total + structure.total/> 
    <#assign xuhao = total-structure.total + 1 />
    <#list structure.items?sort_by(["orderNo"]) as i>
       <#if i.type != 6 && i.type != 7><#-- 共享题干题或共享daan -->
            <@show_item father=null item=i index=(i_index+xuhao)/>
        <#else>
        <@show_item father=null item=i index=xuhao/>
        </#if>
     </#list>
     </#if>
     </#if>
</#macro>
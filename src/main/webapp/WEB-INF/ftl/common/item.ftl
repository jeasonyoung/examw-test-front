<#assign answerflag=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]/>
<#assign xuhao = 0/>	<!-- 计算序号 -->
<#assign total = 0/>	<!-- 总数 -->
<#macro option_flag index>
	${answerflag[index]}
</#macro>

<#macro show_item item index>
	<#if item.type == TYPE_SINGLE_VALUE>
		<@item_choose i=item input="radio" index=index/>
	<#elseif item.type == TYPE_MULTY_VALUE || item.type == TYPE_UNCERTAIN_VALUE>
		<@item_choose i=item input="checkbox" index=index/>
	<#elseif item.type == TYPE_JUDGE_VALUE>
		<@item_judge i=item index=index/>
	<#elseif item.type == TYPE_QANDA_VALUE>
	<#elseif item.type == TYPE_SHARE_TITLE_VALUE>
		<@item_share_title i=item index=index/>
	<#elseif item.type == TYPE_SHARE_ANSWER_VALUE>
	<#else>
	</#if>
</#macro>
<#macro item_choose i input index>
	<div class="box fl" item_type="${i.type}" item_id="${i.item.id?default(i.id)}">
       <div class="timu fl" ><a name="1" id="1"></a>
           <i>${index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
            <div class="list">
                <ul>
                <#if i.item.children??>
                <#list i.item.children as option>
                <li option_id="${option.id}" option_type="${input}" class="out"><i><@option_flag option_index/>.</i><em>${option.content}</em></li>
                </#list>
                <#else>
                <#list i.children as option>
                <li option_id="${option.id}" option_type="${input}" class="out"><i><@option_flag option_index/>.</i><em>${option.content}</em></li>
                </#list>
                </#if>
                </ul>
            </div>
            <div class="abcd">
                <ul>
                <#if i.item.children??>
                <#list i.item.children as option>
                <li class="off" option_id="${option.id}" option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#list>
                <#else>
                <#list i.children as option>
                <li class="off" option_id="${option.id}" option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#list>
                </#if>
                </ul>
             </div>
         </div>
    </div>
</#macro>
<#macro item_judge i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.item.id?default(i.id)}">
        <div class="timu fl" ><a name="10" id="10"></a>
            <i>${index}.</i>
            <em><span>[${i.typeName}]</span>${i.content}</em>
        </div>
        <div class="xz-daan fl">
            <div class="abcd">
               <ul>
                 <li class="off" actual="true" pid="${i.id}" option_id="${i.item.id}_${ANSWER_JUDGE_RIGTH}" option_value="${ANSWER_JUDGE_RIGTH}" option_type="radio">对</li>
                 <li class="off" actual="true" pid="${i.id}" option_id="${i.item.id}_${ANSWER_JUDGE_WRONG}" option_value="${ANSWER_JUDGE_WRONG}" option_type="radio">错</li>
            	</ul>
              </div>
          </div>
    </div>
</#macro>
<#macro item_share_title i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.item.id}"><i>${i.typeName}</i><em>${i.content}</em></div>
		<#list i.item.children as child>
        	<@show_item child index+child_index/>
        </#list>
        <#assign xuhao = xuhao + i.item.children?size />
</#macro>

<#macro answer_card items>
	<#list items as item>
		<#if item_index == 0>
			<div class="five fl">
       			<div class="list">
          			<ul>
		</#if>
		<li><a href="javascript:void(0)" onclick="focusTo(this,${item_index+1})" item_id="${item.id}">${item_index+1}</a></li>
		<#if item_index != 0 && (item_index+1)%5==0>
			 	</ul>
        	</div>
     	</div>
     	<div class="five fl">
       			<div class="list">
          			<ul>
		</#if>
	</#list>
           </ul>
        </div>
     </div>
</#macro>
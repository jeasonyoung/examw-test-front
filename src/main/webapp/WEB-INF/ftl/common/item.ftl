<#assign answerflag=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]/>
<#macro option_flag index>
	${answerflag[index]}
</#macro>

<#macro show_item item>
	<#if item.type == TYPE_SINGLE_VALUE>
		<@item_choose i=item input="radio"/>
	<#elseif item.type == TYPE_MULTY_VALUE || item.type == TYPE_UNCERTAIN_VALUE>
		<@item_choose i=item input="checkbox"/>
	<#elseif item.type == TYPE_JUDGE_VALUE>
		<@item_judge item/>
	<#elseif item.type == TYPE_QANDA_VALUE>
	<#elseif item.type == TYPE_SHARE_TITLE_VALUE>
		<@item_share_title item/>
	<#elseif item.type == TYPE_SHARE_ANSWER_VALUE>
	<#else>
	</#if>
</#macro>
<#macro item_choose i input>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}">
       <div class="timu fl" ><a name="1" id="1"></a>
           <i>1.</i>
              <em><span>[${i.typeName}]</span>${i.content}</em>
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
<#macro item_judge i>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}">
        <div class="timu fl" ><a name="10" id="10"></a>
            <i>10.</i>
            <em><span>[${i.typeName}]</span>${i.content}</em>
        </div>
        <div class="xz-daan fl">
            <div class="abcd">
               <ul>
                 <li class="off" pid="${i.id}" option_id="${ANSWER_JUDGE_RIGTH}" option_type="radio">对</li>
                 <li class="off" pid="${i.id}" option_id="${ANSWER_JUDGE_WRONG}" option_type="radio">错</li>
            	</ul>
              </div>
          </div>
    </div>
</#macro>
<#macro item_share_title i>
	<div class="fenxiti fl" ><i>${i.typeName}</i><em>${i.content}</em></div>
		<#list i.item.children as child>
        	<@show_item child/>
        </#list>
</#macro>
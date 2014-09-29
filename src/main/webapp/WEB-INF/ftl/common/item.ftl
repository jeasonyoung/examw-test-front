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
		<@item_qanda i=item index=index/>
	<#elseif item.type == TYPE_SHARE_TITLE_VALUE>
		<@item_share_title i=item index=index/>
	<#elseif item.type == TYPE_SHARE_ANSWER_VALUE>
		<@item_share_answer i=item index=index/>
	<#else>
	</#if>
</#macro>
<#macro item_choose i input index>
	<div class="box fl" item_type="${i.type}" item_id="${i.item.id?default(i.id)}" item_index="${index}">
		<#if i.item?? && i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
       <div class="timu fl" >
           <i>${index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
            <div class="list">
                <ul>
                <#if i.item.children??>
                <!-- 按顺序输出?sort_by(["orderNo"]) -->
                <#list i.item.children?sort_by(["orderNo"]) as option>
                <li item_index="${index}" option_id="${option.id}" s_item_id="${i.structureItemId?default(i.id)}" option_type="${input}" <#if i.item.userAnswer?contains(option.id)>class="over"<#else>class="out"</#if>><i><@option_flag option_index/>.</i><em>${option.content}</em></li>
                </#list>
                <#else>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li item_index="${index}" option_id="${option.id}" s_item_id="${i.structureItemId?default(i.id)}" option_type="${input}" <#if i.userAnswer?contains(option.id)>class="over"<#else>class="out"</#if>><i><@option_flag option_index/>.</i><em>${option.content}</em></li>
                </#list>
                </#if>
                </ul>
            </div>
            <div class="abcd">
                <ul>
                <#if i.item.children??>
                <#list i.item.children?sort_by(["orderNo"]) as option>
                <li item_index="${index}" <#if i.item.userAnswer?contains(option.id)>class="choose"<#else>class="off"</#if> option_id="${option.id}" s_item_id="${i.structureItemId?default(i.id)}" option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#list>
                <#else>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li item_index="${index}" <#if i.userAnswer?contains(option.id)>class="choose"<#else>class="off"</#if> option_id="${option.id}" s_item_id="${i.structureItemId?default(i.id)}" option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#list>
                </#if>
                </ul>
             </div>
         </div>
    </div>
</#macro>
<#macro item_judge i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.item.id?default(i.id)}" item_index="${index}">
		<#if i.item?? && i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
        <div class="timu fl" >
            <i>${index}.</i>
            <em><span>[${i.typeName}]</span>${i.content}</em>
        </div>
        <div class="xz-daan fl">
            <div class="abcd">
               <ul>
                 <li item_index="${index}" <#if (i.userAnswer?? && i.userAnswer == ANSWER_JUDGE_RIGTH)||(i.item?? && i.item.userAnswer == ANSWER_JUDGE_RIGTH)>class="choose"<#else>class="off"</#if> actual="true" pid="${i.id}" s_item_id="${i.structureItemId?default(i.id)}" option_id="${i.item.id}_${ANSWER_JUDGE_RIGTH}" option_value="${ANSWER_JUDGE_RIGTH}" option_type="radio">对</li>
                 <li item_index="${index}" <#if (i.userAnswer?? && i.userAnswer == ANSWER_JUDGE_WRONG)||(i.item?? && i.item.userAnswer == ANSWER_JUDGE_WRONG)>class="choose"<#else>class="off"</#if> actual="true" pid="${i.id}" s_item_id="${i.structureItemId?default(i.id)}" option_id="${i.item.id}_${ANSWER_JUDGE_WRONG}" option_value="${ANSWER_JUDGE_WRONG}" option_type="radio">错</li>
            	</ul>
              </div>
         </div>
    </div>
</#macro>
<#macro item_qanda i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.item.id?default(i.id)}" item_index="${index}">
       <div class="timu fl" id="font14"><a name="13" id="13"></a>
           <i>${index}.</i>
           <em><span id="cailiao14">[${i.typeName}]<a></a></span>${i.content}</em>
       </div>
       <div class="xz-daan fl">
            <textarea name="" cols="" rows="" class="wenben"></textarea>
       </div>
    </div>
</#macro>
<!-- 共享题干 -->
<#macro item_share_title i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.item.id}"><i>${i.typeName}</i><em>${i.content}</em></div>
		<#list i.item.children?sort_by(["orderNo"]) as child>
        	<@show_item child index+child_index/>
        </#list>
        <#assign xuhao = xuhao + i.item.children?size />
</#macro>
<!-- 共享答案 -->
<#macro item_share_answer i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.item.id}">
		<i>${i.typeName}</i>
		<em>${i.content}<br/>
		<#list i.item.children?sort_by(["orderNo"]) as child>
			<#if child_index != (i.item.children?size - 1)>
				${child.content}<br/>
			</#if>
		</#list>
		</em>
	</div>
		<#assign share_answer_item = i.item.children?last/>
		<@show_share_answer_item share_answer_item i index/>
        <#assign xuhao = xuhao + share_answer_item.children?size />
</#macro>
<#macro show_share_answer_item items parent index>
	<#list items.children as i>
		<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_index="${index+i_index}">
		<#if i.item?? && i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
       <div class="timu fl" >
           <i>${index+i_index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
            <div class="abcd">
                <ul>
                <#if parent.item.children??>
                <#list parent.item.children?sort_by(["orderNo"]) as option>
                <#if i_index != (parent.item.children?size-1)>
                <li item_index="${index+i_index}" <#if i.userAnswer?contains(option.id)>class="choose"<#else>class="off"</#if> option_id="${option.id}" s_item_id="${i.structureItemId?default(i.id)}" option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#if>
                </#list>
                <#else>
                <#list parent.children?sort_by(["orderNo"]) as option>
                <#if i_index != (parent.children?size-1)>
                <li item_index="${index+i_index}" <#if i.userAnswer?contains(option.id)>class="choose"<#else>class="off"</#if> option_id="${option.id}" s_item_id="${i.structureItemId?default(i.id)}" option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#if>
                </#list>
                </#if>
                </ul>
             </div>
         </div>
    </div>
	</#list>
</#macro>
<!-- 计算正确答案 -->
<#macro calculate_right_answer i>
	 <#if i.item.children??>
          <#list i.item.children as option>
          	<#if i.item.answer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
          	</#if>
          </#list>
     <#else>
     	 <#list i.children as option>
          	<#if i.answer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
          	</#if>
         </#list>
     </#if>
</#macro>

<!-- 答题卡 -->
<#macro answer_card items>
	<#list items as item>
		<#if item_index == 0>
			<div class="five fl">
       			<div class="list">
          			<ul>
		</#if>
		<li><a <#if item.userAnswer ??>class="yida"</#if>href="javascript:void(0)" onclick="focusTo(this,${item_index+1})" item_id="${item.id}" s_item_id="${item.structureItemId}">${item_index+1}</a></li>
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
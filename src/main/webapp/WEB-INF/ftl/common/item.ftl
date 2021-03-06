<#assign answerflag=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]/>
<#assign xuhao = 0/>	<#-- 计算序号 -->
<#assign total = 0/>	<#-- 总数 -->
<#-- 计算序号对应的ABCD -->
<#macro option_flag index>${answerflag[index]}</#macro>

<#macro show_item father item index>
	<#if item.type == TYPE_SINGLE_VALUE>
		<@item_choose parent=father i=item input="radio" index=index/>
	<#elseif item.type == TYPE_MULTY_VALUE || item.type == TYPE_UNCERTAIN_VALUE>
		<@item_choose parent=father i=item input="checkbox" index=index/>
	<#elseif item.type == TYPE_JUDGE_VALUE>
		<@item_judge parent=father i=item index=index/>
	<#elseif item.type == TYPE_QANDA_VALUE>
		<@item_qanda parent=father i=item index=index/>
	<#elseif item.type == TYPE_SHARE_TITLE_VALUE>
		<@item_share_title i=item index=index/>
	<#elseif item.type == TYPE_SHARE_ANSWER_VALUE>
		<@item_share_answer i=item index=index/>
	<#else>
	</#if>
</#macro>
<#-- 选择题 -->
<#macro item_choose parent i input index>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_index="${index}">
		<#--
		<#if parent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${parent.content}</em>
		</div>
		</#if>
		-->
       <div class="timu fl" >
           <i>${index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
            <div class="list">
                <ul>
                <#if i.children??>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li item_index="${index}" option_id="${option.id}" record_id='${i.recordId?default("0")}' s_item_id="${i.id}" <#if parent??>pid="${parent.id}"</#if> option_type="${input}" <#if i.userAnswer?contains(option.id)>class="over"<#else>class="out"</#if>><i><@option_flag option_index/>．</i><em><#if option.content?matches("[A-Z]{1}[.][\\W\\w]*")>${option.content?substring(2)}<#else>${option.content}</#if></em></li>
                </#list>
                </#if>
                </ul>
            </div>
            <div class="abcd">
                <ul>
                <#if i.children??>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li item_index="${index}" <#if i.userAnswer?contains(option.id)>class="choose"<#else>class="off"</#if> record_id='${i.recordId?default("0")}' option_id="${option.id}" s_item_id="${i.id}" <#if parent??>pid="${parent.id}"</#if> option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#list>
                </#if>
                </ul>
             </div>
         </div>
    </div>
</#macro>
<#-- 判断题 -->
<#macro item_judge parent i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_index="${index}">
		<#--
		<#if parent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${parent.content}</em>
		</div>
		</#if>
		-->
        <div class="timu fl" >
            <i>${index}.</i>
            <em><span>[${i.typeName}]</span>${i.content}</em>
        </div>
        <div class="xz-daan fl">
            <div class="abcd">
               <ul>
                 <li item_index="${index}" record_id='${i.recordId?default("0")}' <#if (i.userAnswer?? && i.userAnswer == ANSWER_JUDGE_RIGTH)>class="choose"<#else>class="off"</#if> actual="true" <#if parent??>pid="${parent.id}"</#if> s_item_id="${i.id}" option_id="${i.id}_${ANSWER_JUDGE_RIGTH}" option_value="${ANSWER_JUDGE_RIGTH}" option_type="radio">对</li>
                 <li item_index="${index}" record_id='${i.recordId?default("0")}' <#if (i.userAnswer?? && i.userAnswer == ANSWER_JUDGE_WRONG)>class="choose"<#else>class="off"</#if> actual="true" <#if parent??>pid="${parent.id}"</#if> s_item_id="${i.id}" option_id="${i.id}_${ANSWER_JUDGE_WRONG}" option_value="${ANSWER_JUDGE_WRONG}" option_type="radio">错</li>
            	</ul>
              </div>
         </div>
    </div>
</#macro>
<#-- 问答题   -->
<#macro item_qanda parent i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_index="${index}">
       <div class="timu fl">
           <i>${index}.</i>
           <em><span id="cailiao14">[${i.typeName}]<a></a></span>${i.content}</em>
       </div>
       <div class="xz-daan fl">
            <textarea onblur="answerTextArea(this)" item_id="${i.id}" <#if parent??>pid="${parent.id}"</#if> name="item_text" cols="" rows="" class="wenben"><#if i.userAnswer??>${i.userAnswer}</#if></textarea>
       </div>
    </div>
</#macro>
<#-- 共享题干 -->
<#macro item_share_title i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.id}"><i>${i.typeName}</i><em><#if i.content?matches("\\s*[(][一二三四五六七八九十]{1}[)][\\W\\w]*")>${(i.content?trim)?substring(3)}<#else>${i.content}</#if></em></div>
		<#list i.children?sort_by(["orderNo"]) as child>
        	<@show_item i child index+child_index/>
        </#list>
        <#if MODEL == "multi">
        <#assign xuhao = xuhao + i.children?size />
        <#else>
        <#assign xuhao = xuhao + i.children?size - 1 />
        </#if>
</#macro>
<#-- 共享答案 -->
<#macro item_share_answer i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.id}">
		<i>${i.typeName}</i>
		<em>${i.content}<br/>
		<div class="share_answer">
		<#if i.children??>
		<#list i.children?sort_by(["orderNo"]) as child>
			<#if child_index != (i.children?size - 1)>
				<@option_flag child_index/>. ${child.content}<br/>
			</#if>
		</#list>
		</#if>
		</div>
		</em>
	</div>
		<#assign share_answer_item = (i.children?sort_by(["orderNo"]))?last/>
		<@show_share_answer_item share_answer_item i index/>
		<#if MODEL == "multi">
        <#assign xuhao = xuhao + share_answer_item.children?size />
        <#else>
        <#assign xuhao = xuhao + share_answer_item.children?size - 1 />
        </#if>
</#macro>
<#macro item_share_answer_content i>
	<#if i.children??>
	<#list i.children?sort_by(["orderNo"]) as child>
		<#if child_index != (i.children?size - 1)>
			<@option_flag child_index/>. <#if child.content?matches("[A-Z]{1}[.][\\W\\w]*")>${child.content?substring(2)}<#else>${child.content}</#if><br/>
		</#if>
	</#list>
	</#if>
</#macro>
<#macro show_share_answer_item items parent index>
	<#list items.children?sort_by(["orderNo"]) as i>
		<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_index="${index+i_index}">
		<#--<#if i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em><@item_share_answer_content parent/></em>
		</div>
		</#if>-->
       <div class="timu fl" >
           <i>${index+i_index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${parent.id}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
            <div class="abcd">
                <ul>
                <#if parent.children??>
                <#list parent.children?sort_by(["orderNo"]) as option>
                <#if option_index != (parent.children?size-1)>
                <li item_index="${index+i_index}" <#if i.userAnswer?contains(option.id)>class="choose"<#else>class="off"</#if> option_id="${option.id}#${i.id}" s_item_id="${i.id}" pid="${parent.id}" <#if i.type == TYPE_SINGLE_VALUE>option_type="radio"<#else>option_type="checkbox"</#if> actual="true"><@option_flag option_index/></li>
                </#if>
                </#list>
                </#if>
                </ul>
             </div>
         </div>
    </div>
	</#list>
</#macro>
<#-- 计算正确答案 -->
<#macro calculate_right_answer i>
	 <#if i.children??>
	 <#list i.children as option>
       <#if i.answer?index_of(option.id)!=-1>
          	<@option_flag option_index/> 
       </#if>
     </#list>
     </#if>
</#macro>

<#-- 答题卡 -->
<#macro answer_card items>
	<#list items as item>
		<#if item_index == 0>
			<div class="five fl">
       			<div class="list">
          			<ul>
		</#if>
		<li><a <#if item.userAnswer ??>class="yida"</#if>href="javascript:void(0)" onclick="focusTo(this,${item_index+1})" item_id="${item.id}" s_item_id="${item.id}" pid="${item.pid?default(item.id)}">${item_index+1}</a></li>
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
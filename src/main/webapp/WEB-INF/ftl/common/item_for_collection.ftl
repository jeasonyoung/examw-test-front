<#assign answerflag=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]/>
<#assign xuhao = 0/>	<!-- 计算序号 -->
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
	<#else>
	</#if>
</#macro>
<#macro item_choose i input index>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_status="${i.answerStatus}">
		<#if i.parentContent??>
		<div id="font14" class="fenxiti fl" fenxi_item_id="${i.pid}">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
       <div class="timu fl" ><a name="1" id="1"></a>
           <i>${index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
       		<div id="duicuo_${i.id}"></div>
            <div class="list">
                <ul>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li>
                	<i><@option_flag option_index/>.</i>
                	<em>${option.content}</em>
                </li>
                </#list>
                </ul>
            </div>
            <#if !IS_SHOW_ANSWER>
            	<div class="abcd">
                <ul>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li item_index="${index}" class="off" option_id="${option.id}" s_item_id="${i.id}" <#if parent??>pid="${parent.id}"</#if> option_type="${input}" actual="true"><@option_flag option_index/></li>
                </#list>
                </ul>
             	</div>
             </#if>
         </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${i.id}" <#if index != 1>style="display:none"</#if>></div>
               <div item_id="${i.id}" name="answer" <#if index != 1 || !IS_SHOW_ANSWER>style="display:none"</#if>>
               <div class="f-l fl" id="rightAnswer_${i.id}" answer='<@calculate_right_answer i/>'><i>参考答案：</i><em class="dui"><@calculate_right_answer i/></em></div>
               <div class="f-l fl" item_id="${i.id}" style="display:none" id="myAnswer_${i.id}"></div>
               <div class="f-l fl"><i>上次答案：</i><@calculate_user_answer i/></div>
               </div>
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.id}')"><#if index != 1>展开解析<#else>收起解析</#if></a></i><em class="jiexi-h"></em></div>
                    <!--解析展开<div class="f-r fl"><i><a href="#">展开解析</a></i><em class="jiexi"></em></div>-->
                    <!--<div class="f-r fr"><em class="jiucuo"></em><i><a href="#">纠错</a></i></div>-->
                    <#if i.isCollected>
                    <div class="f-r fr"><em class="shoucang-h"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.id}','${i.id}');">移除此收藏</a></i></div>
                    <#else>
                    <div class="f-r fr"><em class="shoucang"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.id}','${i.id}');">收藏</a></i></div>
                    </#if>
               </div>
         </div>
         <@item_analysis i index/>
</#macro>
<#macro item_judge i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_status="${i.answerStatus}">
		<#if i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
        <div class="timu fl" ><a name="10" id="10"></a>
            <i>${index}.</i>
            <em><span>[${i.typeName}]</span>${i.content}</em>
        </div>
        <div class="xz-daan fl">
        	<#if !IS_SHOW_ANSWER>
        	   <div class="abcd">
               <ul>
                 <li item_index="${index}" class="off" actual="true" <#if parent??>pid="${parent.id}"</#if> s_item_id="${i.id}" option_id="${i.id}_${ANSWER_JUDGE_RIGTH}" option_value="${ANSWER_JUDGE_RIGTH}" option_type="radio">对</li>
                 <li item_index="${index}" class="off" actual="true" <#if parent??>pid="${parent.id}"</#if> option_id="${i.id}_${ANSWER_JUDGE_WRONG}" option_value="${ANSWER_JUDGE_WRONG}" option_type="radio">错</li>
            	</ul>
              </div>
        	</#if>
         </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${i.id}" <#if index != 1 ||!IS_SHOW_ANSWER>style="display:none"</#if>></div>
               <div item_id="${i.id}" name="answer">
               <div class="f-l fl"><i>参考答案：</i>
               			<em class="dui">
               			<#if i.answer == ANSWER_JUDGE_RIGTH>
               			对
               			<#else>
               			错
               			</#if>
               			</em>
               	</div>
               <div class="f-l fl"><i>我的答案：</i>
               		    <#if !i.userAnswer?? || i.userAnswer =="">
			 				<em class="weida">未作答</em>
						<#else>
							<#if i.answerStatus == STATUS_RIGHT>
								<em class="dui">
							<#else>
								<em class="cuo">
							</#if>	
               				<#if i.userAnswer == ANSWER_JUDGE_RIGTH>
               				对
               				<#else>
               				错
               				</#if>
               				</em>
               			</#if>
               </div>
               <div>
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.id}')"><#if index != 1>展开解析<#else>收起解析</#if></a></i><em class="jiexi-h"></em></div>
                    <!--解析展开<div class="f-r fl"><i><a href="#">展开解析</a></i><em class="jiexi"></em></div>-->
                    <#if i.isCollected>
                    <div class="f-r fr"><em class="shoucang-h"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.id}','${i.id}');">移除此收藏</a></i></div>
                    <#else>
                    <div class="f-r fr"><em class="shoucang"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.id}','${i.id}');">收藏</a></i></div>
                    </#if>
                    <!--收藏后<div class="f-r fl"><em class="shoucang"></em><i><a href="#">收藏</a></i></div>-->
               </div>
         </div>
         <@item_analysis i index/>
    </div>
</#macro>
<#macro item_qanda i index>
	<div class="box fl">
       <div class="timu fl" id="font14"><a name="13" id="13"></a>
           <i>${index}.</i>
           <em><span id="cailiao14">[${i.typeName}]<a></a></span>${i.content}</em>
       </div>
       <div class="xz-daan fl">
            <textarea name="" cols="" rows="" class="wenben"></textarea>
       </div>
       <div class="daanbox fl">
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.id}')"><#if index != 1>展开解析<#else>收起解析</#if></a></i><em class="jiexi-h"></em></div>
                    <!--解析展开<div class="f-r fl"><i><a href="#">展开解析</a></i><em class="jiexi"></em></div>-->
                    <!--<div class="f-r fr"><em class="jiucuo"></em><i><a href="#">纠错</a></i></div>-->
                    <#if (i.isCollected)>
                    <div class="f-r fr"><em class="shoucang-h"></em><i><a href="javascript:void(0)" <#if parent??>pid="${parent.id}"</#if> onclick="collectOrCancel(this,'${i.id}','${i.userAnswer}');">移除此收藏</a></i></div>
                    <#else>
                    <div class="f-r fr"><em class="shoucang"></em><i><a href="javascript:void(0)" <#if parent??>pid="${parent.id}"</#if> onclick="collectOrCancel(this,'${i.id}','${i.userAnswer}');">收藏</a></i></div>
                    </#if>
               </div>
         </div>
         <div class="jiexi-box fl" name="jiexi" item_id="${(i.id)}" <#if index != 1>style="display:none"</#if>>
         	  <div class="cankaobox fl">
                   <i>我的答案：</i>${i.userAnswer}
              </div>
              <#if i.answer??>
              <div class="cankaobox fl">
                   <i>参考答案：</i>${i.answer}
              </div>
              </#if>
              <#if i.analysis??>
              <div class="cankaobox fl">
                   <i>参考解析：</i>${i.analysis}
              </div>
              </#if>
              <div class="h10"></div>
         <div>
    </div>
</#macro>
<#macro item_share_title i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.id}"><i>${i.typeName}</i><em>${i.content}</em></div>
		<#list i.children as child>
        	<@show_item child index+child_index/>
        </#list>
        <#assign xuhao = xuhao + i.children?size />
</#macro>
<!-- 计算正确答案 -->
<#macro calculate_right_answer i>
     <#list i.children?sort_by(["orderNo"]) as option>
         <#if i.answer?index_of(option.id)!=-1>
          	<@option_flag option_index/> 
         </#if>
     </#list>
</#macro>

<!-- 计算我的答案 -->
<#macro calculate_user_answer i>
     	<#if !i.userAnswer?? || i.userAnswer == "">
			<em class="weida">未作答</em>
		<#else>
			<#if i.answerStatus == STATUS_RIGHT>
				<em class="dui">
			<#else>
				<em class="cuo">
			</#if>
     	 <#list i.children?sort_by(["orderNo"]) as option>
          	<#if i.userAnswer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
          	</#if>
         </#list>
         	</em>
         </#if>
</#macro>
<#macro item_analysis i index>
	<div class="jiexi-box fl" name="jiexi" item_id="${i.id}" <#if index != 1 || !IS_SHOW_ANSWER>style="display:none"</#if>>
              <div class="cankaobox fl">
                   <i>参考解析：</i>${i.analysis}
              </div>
              <div class="h10"></div>
    </div>
</#macro>
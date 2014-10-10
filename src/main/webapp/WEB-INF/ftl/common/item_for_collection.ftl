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
		<#if i.item?? && i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
       <div class="timu fl" ><a name="1" id="1"></a>
           <i>${index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
            <div class="list">
                <ul>
                <#if i.item.children??>
                <!-- 按顺序输出?sort_by(["orderNo"]) -->
                <#list i.item.children?sort_by(["orderNo"]) as option>
                <li>
                	<i><@option_flag option_index/>.</i>
                	<em>${option.content}</em>
                </li>
                </#list>
                <#else>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li>
                	<i><@option_flag option_index/>.</i>
                	<em>${option.content}</em>
                </li>
                </#list>
                </#if>
                </ul>
            </div>
         </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${i.item.id?default(i.id)}" <#if index != 1>style="display:none"</#if>></div>
               <div item_id="${i.item.id?default(i.id)}" name="answer" style="display:none">
               <div class="f-l fl"><i>参考答案：</i><@calculate_right_answer i/></div>
               <div class="f-l fl"><i>我的答案：</i><@calculate_user_answer i/></div>
               </div>
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.item.id?default(i.id)}')"><#if index != 1>展开解析<#else>收起解析</#if></a></i><em class="jiexi-h"></em></div>
                    <!--解析展开<div class="f-r fl"><i><a href="#">展开解析</a></i><em class="jiexi"></em></div>-->
                    <div class="f-r fr"><em class="jiucuo"></em><i><a href="#">纠错</a></i></div>
                    <#if i.item.isCollected?default(i.isCollected)>
                    <div class="f-r fr"><em class="shoucang-h"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.structureItemId?default(i.id)}','${i.item.id?default(i.id)}');">移除此收藏</a></i></div>
                    <#else>
                    <div class="f-r fr"><em class="shoucang"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.structureItemId?default(i.id)}','${i.item.id?default(i.id)}');">收藏</a></i></div>
                    </#if>
               </div>
         </div>
         <@item_analysis i index/>
</#macro>
<#macro item_judge i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.item.id?default(i.id)}" item_status="${i.item.answerStatus?default(i.answerStatus)}">
		<#if i.item?? && i.parentContent??>
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
         </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${i.item.id?default(i.id)}" <#if index != 1>style="display:none"</#if>></div>
               <div item_id="${i.item.id?default(i.id)}" name="answer">
               <div class="f-l fl"><i>参考答案：</i>
               			<em class="dui">
               			<#if i.item??>
               				<#if i.item.answer == ANSWER_JUDGE_RIGTH>
               				对
               				<#else>
               				错
               				</#if>
               			<#else>
               				<#if i.answer == ANSWER_JUDGE_RIGTH>
               				对
               				<#else>
               				错
               				</#if>
               			</#if>
               			</em>
               	</div>
               <div class="f-l fl"><i>我的答案：</i>
               		<#if i.item??>
               			<#if !i.item.userAnswer?? || i.item.userAnswer == "">
			 				<em class="weida">未作答</em>
						<#else>
							<#if i.item.answerStatus == STATUS_RIGHT>
								<em class="dui">
							<#else>
								<em class="cuo">
							</#if>	
               				<#if i.item.userAnswer == ANSWER_JUDGE_RIGTH>
               				对
               				<#else>
               				错
               				</#if>
               				</em>
               			</#if>
               		<#else>
               			<#if !i.userAnswer?? || i.item.userAnswer == "">
			 				<em class="weida">未作答</em>
						<#else>
							<#if i.item.answerStatus == STATUS_RIGHT>
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
               		</#if>
               </div>
               <div>
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.item.id?default(i.id)}')"><#if index != 1>展开解析<#else>收起解析</#if></a></i><em class="jiexi-h"></em></div>
                    <!--解析展开<div class="f-r fl"><i><a href="#">展开解析</a></i><em class="jiexi"></em></div>-->
                    <#if i.item.isCollected?default(i.isCollected)>
                    <div class="f-r fr"><em class="shoucang-h"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.structureItemId?default(i.id)}','${i.item.id?default(i.id)}');">移除此收藏</a></i></div>
                    <#else>
                    <div class="f-r fr"><em class="shoucang"></em><i><a href="javascript:void(0)" onclick="collectOrCancel(this,'${i.structureItemId?default(i.id)}','${i.item.id?default(i.id)}');">收藏</a></i></div>
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
    </div>
</#macro>
<#macro item_share_title i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.item.id}"><i>${i.typeName}</i><em>${i.content}</em></div>
		<#list i.item.children as child>
        	<@show_item child index+child_index/>
        </#list>
        <#assign xuhao = xuhao + i.item.children?size />
</#macro>
<!-- 计算正确答案 -->
<#macro calculate_right_answer i>
	 <em class="dui">
	 <#if i.item.children??>
          <#list i.item.children?sort_by(["orderNo"]) as option>
          	<#if i.item.answer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
          	</#if>
          </#list>
     <#else>
     	 <#list i.children?sort_by(["orderNo"]) as option>
          	<#if i.answer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
          	</#if>
         </#list>
     </#if>
     </em>
</#macro>

<!-- 计算我的答案 -->
<#macro calculate_user_answer i>
	<#if i.item.children??>
		<#if !i.item.userAnswer?? || i.item.userAnswer == "">
			 <em class="weida">未作答</em>
		<#else>
			<#if i.item.answerStatus == STATUS_RIGHT>
				<em class="dui">
			<#else>
				<em class="cuo">
			</#if>
          <#list i.item.children?sort_by(["orderNo"]) as option>
          	<#if i.item.userAnswer?contains(option.id)>
          		<@option_flag option_index/> 
          	</#if>
          </#list>
          </em>
        </#if>
     <#else>
     	<#if !i.userAnswer?? || i.userAnswer == "">
			<em class="weida">未作答</em>
		<#else>
			<#if i.item.answerStatus == STATUS_RIGHT>
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
     </#if>
</#macro>
<#macro item_analysis i index>
	<div class="jiexi-box fl" name="jiexi" item_id="${i.item.id?default(i.id)}" <#if index != 1>style="display:none"</#if>>
              <div class="cankaobox fl">
                   <i>参考解析：</i>${i.item.analysis?default(i.analysis)}
              </div>
              <div class="h10"></div>
              <div class="bookbox fl">
                    <div class="book-l fl"><span>第${index}题笔记</span>（本题共${i.item.totalNoteNum?default(0)}条笔记）</div>
                    <div class="sybook fr"><a href="javascript:void(0)" onclick="showNote('${i.structureItemId?default(i.id)}','${index}','all')">查看所有笔记（${i.item.totalNoteNum?default(0)}条）</a></div>
                    <div class="mybook fr"><a href="javascript:void(0)" onclick="showNote('${i.structureItemId?default(i.id)}','${index}','mine')">查看我的笔记（${i.item.userNoteNum?default(0)}条）</a></div>
                    <textarea name="" class="notebook" id="font14" s_item_id="${i.structureItemId?default(i.id)}" item_id = "${i.item.id?default(i.id)}"></textarea>
                    <div class="sure"><a href="javascript:void(0)" onclick="addNote(this);">确认保存</a></div>
              </div>
              <div name="noteList" s_item_id="${i.structureItemId?default(i.id)}" style="display:none">
              	<div name="content">
              		<div class="textbook fl">
                    	<div class="txt">
                         <div class="pic-bg"><a href="#" target="_blank" title=""></a></div>
                         <div class="pic"><img src="<@s.url "/resources/front-default/image/pic2.jpg"/>" width="70" height="70"></div>
                         <div class="f-right">
                             <div class="vipname fl"><a href="#" target="_blank" title="王艳阳">王艳阳</a><span>1小时前</span></div>
                             <div class="pinglun fl">2—3年</div>
                             <!--<div class="huifu"><a href="#" class="huifu">回复(0)</a></div>
                             <div class="zan"><a href="#" class="zan">(0)</a></div>-->
                         </div>
                      </div>
                    </div>
               	</div>
               <div class="h10"></div>
                <div id="pager${index}" class="pager-plugin">
               </div>
         </div>
    </div>
</#macro>
<#assign answerflag=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]/>
<#assign xuhao = 0/>	<!-- 计算序号 -->
<#assign total = 0/>	<!-- 总数 -->
<#macro option_flag index>${answerflag[index]}</#macro>

<#macro show_item father item index>
	<#if item.type == TYPE_SINGLE_VALUE>
		<@item_choose parent=father i=item input="radio" index=index/>
	<#elseif item.type == TYPE_MULTY_VALUE || item.type == TYPE_UNCERTAIN_VALUE>
		<@item_choose parent=father i=item input="checkbox" index=index/>
	<#elseif item.type == TYPE_JUDGE_VALUE>
		<@item_judge parent=father i=item index=index/>
	<#elseif item.type == TYPE_QANDA_VALUE>
		<@item_qanda i=item index=index/>
	<#elseif item.type == TYPE_SHARE_TITLE_VALUE>
		<@item_share_title i=item index=index/>
	<#elseif item.type == TYPE_SHARE_ANSWER_VALUE>
		<@item_share_answer i=item index=index/>
	<#else>
	</#if>
</#macro>
<#macro item_choose parent i input index>
	<div name="item_whole" class="box fl" item_type="${i.type}" item_id="${i.id}" item_status="${i.answerStatus?default(0)}">
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
       		<#if i.answerStatus == STATUS_RIGHT>
       			<div class="dui"></div>
       		<#else>	
       			<div class="cuo"></div>
       		</#if>
       		<#if i.children??>
            <div class="list">
                <ul>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li class="out"><#if i.answer?contains(option.id)><span><@option_flag option_index/>. </span>
                	<#else>
                	<i><@option_flag option_index/>．</i>
                	</#if>
                	<em><#if option.content?matches("[A-Z]{1}[.][\\W\\w]*")>${option.content?substring(2)}<#else>${option.content}</#if></em>
                </li>
                </#list>
                </ul>
            </div>
            </#if>
         </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${i.id}" <#if index != 1>style="display:none"</#if>></div>
               <div class="f-l fl"><i>参考答案：</i><@calculate_right_answer parent i/></div>
               <div class="f-l fl"><i>我的答案：</i><@calculate_user_answer parent i/></div>
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
         <@item_analysis i index/>
    </div>
</#macro>
<#macro item_judge parent i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_status="${i.answerStatus}">
		<#if i.item?? && i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
        <div class="timu fl" ><a name="10" id="10"></a>
            <i>${index}.</i>
            <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
        </div>
        <div class="xz-daan fl">
       		<#if i.answerStatus == STATUS_RIGHT>
       			<div class="dui"></div>
       		<#else>	
       			<div class="cuo"></div>
       		</#if>
         </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${(i.id)}" <#if index != 1>style="display:none"</#if>></div>
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
               			<#if !i.userAnswer??>
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
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.id}')"><#if index != 1>展开解析<#else>收起解析</#if></a></i><em class="jiexi-h"></em></div>
                    <!--解析展开<div class="f-r fl"><i><a href="#">展开解析</a></i><em class="jiexi"></em></div>-->
                    <#if (i.isCollected)>
                    <div class="f-r fr"><em class="shoucang-h"></em><i><a href="javascript:void(0)" <#if parent??>pid="${parent.id}"</#if> onclick="collectOrCancel(this,'${i.id}','${i.userAnswer}');">移除此收藏</a></i></div>
                    <#else>
                    <div class="f-r fr"><em class="shoucang"></em><i><a href="javascript:void(0)" <#if parent??>pid="${parent.id}"</#if> onclick="collectOrCancel(this,'${i.id}','${i.userAnswer}');">收藏</a></i></div>
                    </#if>
                    <!--收藏后<div class="f-r fl"><em class="shoucang"></em><i><a href="#">收藏</a></i></div>-->
               </div>
         </div>
         <@item_analysis i index/>
    </div>
</#macro>
<!-- 问答题 -->
<#macro item_qanda i index>
	<div class="box fl">
       <div class="timu fl"></a>
           <i>${index}.</i>
           <em><span id="cailiao14">[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl">
            <textarea readonly="readonly" name="" cols="" rows="" class="wenben">${i.userAnswer}</textarea>
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
<!-- 共享题干题  -->
<#macro item_share_title i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.id}"><i>${i.typeName}</i><em>${i.content}</em></div>
		<#list i.children?sort_by(["orderNo"]) as child>
        	<@show_item null child index+child_index/>
        </#list>
        <#assign xuhao = xuhao + i.children?size />
</#macro>
<!-- 共享答案题   -->
<#macro item_share_answer i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.id}">
		<i>${i.typeName}</i>
		<em>${i.content}<br/>
		<div class="share_answer">
		<#list i.children?sort_by(["orderNo"]) as child>
			<#if child_index != (i.children?size - 1)>
				<@option_flag child_index/>. ${child.content}<br/>
			</#if>
		</#list>
		</div>
		</em>
	</div>
	<#assign share_answer_item = (i.children?sort_by(["orderNo"]))?last/>
	<@show_share_answer_item share_answer_item i index/>
    <#assign xuhao = xuhao + share_answer_item.children?size />
</#macro>
<#macro item_share_answer_content i>
	<#list i.children?sort_by(["orderNo"]) as child>
		<#if child_index != (i.children?size - 1)>
			<@option_flag child_index/>. ${child.content}<br/>
		</#if>
	</#list>
</#macro>
<#macro show_share_answer_item items parent index>
	<#list items.children as i>
		<@show_item parent i index+i_index/>
	</#list>
</#macro>
<!-- 计算正确答案 -->
<#macro calculate_right_answer parent i>
	 <em class="dui">
	 <#if parent??>	<!-- 共享答案题  -->
	 	<#list parent.children?sort_by(["orderNo"]) as option>
        	<#if i.answer?index_of(option.id)!=-1>
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
<#macro calculate_user_answer parent i>
     <#if !i.userAnswer?? || i.userAnswer == "">
		<em class="weida">未作答</em>
	<#else>
		<#if i.answerStatus == STATUS_RIGHT>
			<em class="dui">
		<#else>
			<em class="cuo">
		</#if>
     	<#if parent??>	<!-- 共享答案题  -->
	 	<#list parent.children?sort_by(["orderNo"]) as option>
        	<#if i.userAnswer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
        	</#if>
     	</#list>
	 	<#else>
     	<#list i.children?sort_by(["orderNo"]) as option>
        	<#if i.userAnswer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
        	</#if>
     	</#list>
     	</#if>
         	</em>
         </#if>
</#macro>
<#macro item_analysis i index>
	<div class="jiexi-box fl" name="jiexi" item_id="${(i.id)}" <#if index != 1>style="display:none"</#if>>
              <div class="cankaobox fl">
                   <i>参考解析：</i>${i.analysis}
              </div>
              <div class="h10"></div>
              <!--
              <div class="bookbox fl">
                    <div class="book-l fl"><span>第${index}题笔记</span>（本题共${i.totalNoteNum?default(0)}条笔记）</div>
                    <div class="sybook fr"><a href="javascript:void(0)" onclick="showNote('${i.id}','${index}','all')">查看所有笔记（${i.totalNoteNum?default(0)}条）</a></div>
                    <div class="mybook fr"><a href="javascript:void(0)" onclick="showNote('${i.id}','${index}','mine')">查看我的笔记（${i.userNoteNum?default(0)}条）</a></div>
                    <textarea name="" class="notebook" id="font14" s_item_id="${i.id}" item_id = "${i.id}"></textarea>
                    <div class="sure"><a href="javascript:void(0)" onclick="addNote(this);">确认保存</a></div>
              </div>
              <div name="noteList" s_item_id="${i.id}" style="display:none">
              	<div name="content">
              		<div class="textbook fl">
                    	<div class="txt">
                         <div class="pic-bg"><a href="#" target="_blank" title=""></a></div>
                         <div class="pic"><img src="<@s.url "/resources/front-default/image/pic2.jpg"/>" width="70" height="70"></div>
                         <div class="f-right">
                             <div class="vipname fl"><a href="#" target="_blank" title="王艳阳">王艳阳</a><span>1小时前</span></div>
                             <div class="pinglun fl">2—3年</div>
                             <div class="huifu"><a href="#" class="huifu">回复(0)</a></div>
                             <div class="zan"><a href="#" class="zan">(0)</a></div>
                         </div>
                      </div>
                    </div>
               	</div>
               <div class="h10"></div>
                <div id="pager${index}" class="pager-plugin">
               </div>
         	</div>
          -->
    </div>
</#macro>
<!-- 答题卡 -->
<#macro answer_card items>
	<#list items as item>
		<#if item_index == 0>
			<div class="five fl">
       			<div class="list">
          			<ul>
		</#if>
		<li item_status="${item.answerStatus}"><a <#if item.answerStatus == STATUS_RIGHT>class="dui"<#else>class="cuo"</#if> href="javascript:void(0)" onclick="focusTo(this,${item_index+1})" item_id="${item.id}" s_item_id="${item.structureItemId}">${item_index+1}</a></li>
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
<#macro show_chapter chapterList>
<#list CHAPTERLIST?sort_by(["orderNo"]) as father>
	<li class="chapter-out" onMouseOver="this.className='chapter-over'" onMouseOut="this.className='chapter-out'">
        <div class="title">
        <div id="${father.id}" name="father" >
        <#if father.children??>
            <#if father_index == 0>
                <div class="jian"></div>
            <#else>
                <div class="jia"></div>
            </#if>
        <#else>
            <div class="jian"></div>	
        </#if>
        <em>${father.title}</em>
        </div>
        <span>0</span>
        <span class="red">0</span>
        <span><div class="buy-botton"><div class="hei-botton"><a href="#">开始做题</a></div></div></span>
        </div>
    </li>
    <#if father.children?? && (father.children?size>0)>
    	<#list father.children?sort_by(["orderNo"]) as sun>
    	<#assign cycle = 1 />
    	<li <#if father_index != 0>style="display:none;overflow:hidden;"</#if> name="child" tpid="${father.id}" pid="${father.id}" sid="${sun.id}" class="chapter-out" onMouseOver="this.className='chapter-over'" onMouseOut="this.className='chapter-out'">
           <div class="title">
               <div id="${sun.id}" name="father" >
                  <#if sun.children??>
                       <div class="jia kong"></div>
                  <#else>
                       <div class="jian kong"></div>	
                  </#if>
                  <i class="i${cycle}">${sun.title}</i></div>
                  <span>0</span>
                  <span>0</span>
                  <span><div class="buy-botton"><div class="hei-botton"><a href="#">开始做题</a></div></div></span>
        </li>
        <@show_chapter_child father sun  1 cycle/>
    	</#list>
    </#if>
</#list>
</#macro>
<#macro show_chapter_child father chapter index cycleCount>
	<#if chapter.children?? && (chapter.children?size>0)>
	   <#assign cycle = cycleCount + 1 />
       <#list chapter.children?sort_by(["orderNo"]) as sun>
       <#if sun.children?? && (sun.children?size>0)>
       <li <#if index != 0>style="display:none;overflow:hidden;"</#if> name="child" <#if father??>tpid="${father.id}"</#if> pid="${chapter.id}" sid="${sun.id}" class="chapter-out" onMouseOver="this.className='chapter-over'" onMouseOut="this.className='chapter-out'">
           <div class="title">
               <div id="${sun.id}" name="father" >
                  <#if sun.children??>
                       <div class="jia kong${cycle}"></div>
                  <#else>
                       <div class="jian kong${cycle}"></div>	
                  </#if>
                  <i class="i${cycle}">${sun.title}</i></div>
                  <span>0</span>
                  <span>0</span>
                  <span><div class="buy-botton"><div class="hei-botton"><a href="#">开始做题</a></div></div></span>
        </li>
        <@show_chapter_child chapter sun sun_index cycle/>
        <#else>
        <@show_chapter_child chapter sun sun_index cycle/>
        </#if>
        </#list>
     <#else>
     	<li class="chapter-out" <#if father??>pid="${father.id}"<#else>pid="${chapter.id}"</#if> style="display:none;overflow:hidden;" onMouseOver="this.className='chapter-over'" onMouseOut="this.className='chapter-out'">
             <div class="title">
             <div class="jian kong${cycle} bg-no"></div>
             <i class="i${cycle}">&middot; 
             	<#if father??>
             	<a href="<@s.url "/library/chapter/detail/${father.id}/${chapter.id}"/>" target="_blank" title="${chapter.title}">${chapter.title}</a>
             	<#else>
             	${chapter.title}
             	</#if>
             </i>
             <span></span>
             <span></span>
             <span><div class="buy-botton"><div class="hei-botton">
             	<#if father??>
             	<a href="<@s.url "/library/chapter/detail/${father.id}/${chapter.id}"/>" target="_blank">查看知识点</a>
             	<#else>
             	<a href="#">开始做题</a>
             	</#if>
             </div></div></span>
             </div>
         </li>
     </#if>
</#macro>
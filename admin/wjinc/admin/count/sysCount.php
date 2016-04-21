<?php
	if("用户名" != $_GET['username'] && ""!=$_GET['username']){
		$uname = " and username='".$_GET['username']."'";
	}else{
		$uname = "";
	}
	$sql = "select * from {$this->prename}links where 1=1{$uname} and isDelete=0 order by lid desc";
	$data=$this->getPage($sql,$this->page,$this->pageSize);
?>
<article class="module width_full">
	<header>
		<h3 class="tabs_involved">注册链接
			<div class="submit_link wz"><input type="submit" value="添加注册链接" onclick="sysModal('business/linkModal/0/add','添加注册链接',480)" class="alt_btn"></div>
			<form action="/admin778899.php/business/linkList" class="submit_link wz" target="ajax" dataType="html" call="defaultSearch">
         		   用户：<input type="text" class="alt_btn" style="width:60px;" value="用户名" name="username"/>&nbsp;&nbsp;
				<input type="submit" value="查找" class="alt_btn">
				<input type="reset" value="重置条件">
			</form>
		</h3>
	</header>
	<table class="tablesorter" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>ID</th>
        		<th>用户名</th>
		        <th>状态</th>
		        <th>注册人数</th>
		        <th>账号类型</th>
		        <th>返点</th>
		        <th>不定位返点</th>
		        <th>初始金额</th>
		        <th>找客服</th>
		        <th>虚拟号</th>
		        <th>创建时间</th>
		        <th>操作</th>
			</tr>
		</thead>
		<tbody>
		<?php if($data['data']) foreach($data['data'] as $var){ ?>
			<tr>
		        <td><?=$var['lid'] ?></td>
		        <td><?=$var['username'] ?></td>
		        <td><?=$this->iff($var['enable'],'在用','停用') ?></td>
		        <td><?=$var['count'] ?></td>
		        <td><?=$this->iff($var['type'],'代理号','会员号') ?></td>
		        <td><?=$var['fanDian'] ?></td>
		        <td><?=$var['fanDianBdw'] ?></td>
		        <td><?=$var['coin'] ?></td>
		        <td><?=$this->iff($var['kf'],'允许','不允许') ?></td>
		        <td><?=$this->iff($var['iv'],'是','否') ?></td>
		        <td><?=date('Y-m-d H:i',$var['regTime']) ?></td>
		        <td align="center">
		        	<a href="/admin778899.php/business/linkDelete/<?=$var['lid'] ?>" target="ajax" call="sysReload" dataType="json">删除</a> |
		        	<a href="/admin778899.php/business/switchLinkStatus/<?=$var['lid'] ?>" target="ajax" call="sysReload"><?=$this->iff($var['enable'],'停用','启用') ?></a> | 
		        	<a href="#" onclick="sysModal('business/linkModal/<?=$var['lid'] ?>/update','修改注册链接',480)">修改</a>
		        </td>
		    </tr>
		<?php }else{?>
			<tr>
		        <td colspan="8" align="center">暂时没有注册链接。</td>
		    </tr>
		<?php } ?>
		</tbody>
	</table>
	<footer>
		<?php 
		$rel=get_class($this).'/linkList-{page}?'.http_build_query($_GET,'','&');
		$this->display('inc/page.php',0,$data['total'],$rel,'defaultReplacePageAction');
		?>
	</footer>
</article>
<?php require APPROOT . '/views/inc/header.php'; ?>
    <?php flash('farmer_message'); ?>
  <div class="row ">
      <div class="col-md-8">
          <h2>Famers</h2>
      </div>
      <div class="col-md-4">
          <a class="btn btn-primary pull-right" href="<?php echo URLROOT ;?>/farmers/add"><i class="fa fa-pencil"></i> Add Farmer</a>
      </div>
  </div>
  <?php foreach ($data['farmers'] as $farmer) : ?>
        <div class="card mb-3 mt-2">
            <div class="card-body"><h2 class="card-text"><?php echo  $farmer->fullname ;?></h2></div>
            <div class="card-body"><h2 class="card-text"><?php echo  $farmer->telno ;?></h2></div>
            <a href="<?php echo URLROOT ;?>/farmers/show/<?php echo $farmer->farmerId ;?>" class="btn btn-dark btn-block">More...</a>
        </div>
  <?php endforeach ;?>
<?php require APPROOT . '/views/inc/footer.php'; ?>
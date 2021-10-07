<?php require APPROOT . '/views/inc/header.php'; ?>
  <div class="row ">
      <div class="col-md-4">
          <a class="btn btn-primary pull-right" href="<?php echo URLROOT ;?>/farmers"><i class="fa fa-backward"></i> Go Back</a>
      </div>
  </div>
        <div class="card mb-3 mt-2">
            <div class="card-body"><h2 class="card-text"><?php echo  $data['farmer']->title ;?></h2></div>
            <p class="card-body">
                <?php echo  $data['farmer']->body ;?>
            </p>
            <p class="card-title bg-light p-2 mb-3">
             Created By <?php echo $data['user']->name ;?> on <?php echo  $data['farmer']->created_at ;?>
            </p>

            <?php if($data['farmer']->user_id == $_SESSION['user_id']) : ?>
                <div class="row">
                    <div class="col">
                        <a href="<?php echo URLROOT ;?>/farmers/edit/<?php echo $data['farmer']->id ;?>" class="btn btn-dark btn-block">Edit</a>
                    </div>
                    <div class="col">
                        <form class="pull-right" action="<?php echo URLROOT ;?>/farmers/delete/<?php echo $data['farmer']->id ;?>" method="farmer">
                            <input type="submit" class="btn btn-danger btn-block" value="Delete"> 
                        </form>
                    </div>
                </div>
            <?php endif ;?>
        </div>
<?php require APPROOT . '/views/inc/footer.php'; ?>
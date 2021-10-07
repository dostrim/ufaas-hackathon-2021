<?php require APPROOT . '/views/inc/header.php'; ?>
<div class="row">
    <div class="col-md-8 mx-auto">
        <div class="card bg-light mt-5">
            <div class="card-header card-text">
                <div class="row">
                    <div class="col">
                        <h2 class="card-text">Edit Farmer</h2>
                    </div>
                    <div class="col">
                        <a href="<?php echo URLROOT ;?>/famers" class="btn btn-light pull-right"><i class="fa fa-backward"></i> Back</a>
                    </div>
                    
                </div>
                <p class="card-text">Please enter your username and password</p>
            </div>
        
            <div class="card-body">
                <form method="farmer" action="<?php echo URLROOT ;?>/Farmers/edit/<?php echo $data['id'] ;?>">
                    <div class="form-group">
                        <label for="fullname">Full Name<sub>*</sub></label>
                        <input type="text" name="fullname" class="form-control form-control-lg <?php echo (!empty($data['fullname_err'])) ? 'is-invalid' : '' ;?>" value="<?php echo $data['fullname'] ;?>">
                        <span class="invalid-feedback"><?php echo $data['title_err'] ;?> </span>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Telphone<sub>*</sub></label>
                        <textarea type="text" name="telno" class="form-control form-control-lg <?php echo (!empty($data['telno_err'])) ? 'is-invalid' : '' ;?>"><?php echo $data['telno'] ;?></textarea><span class="invalid-feedback"><?php echo $data['telno_err'] ;?> </span>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col">
                                <input type="submit" class="btn btn-primary btn-block pull-left" value="Update Farmer">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<?php require APPROOT . '/views/inc/footer.php'; ?>
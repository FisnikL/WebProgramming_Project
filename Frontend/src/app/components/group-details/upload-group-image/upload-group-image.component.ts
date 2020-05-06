import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-upload-group-image',
  templateUrl: './upload-group-image.component.html',
  styleUrls: ['./upload-group-image.component.css']
})
export class UploadGroupImageComponent implements OnInit {

  @Input() groupCode: string;
  @Output() imageUploaded = new EventEmitter<boolean>();
  imageName: any;
  selectedFile: File;
  // isFileSelected: boolean;
  retrievedImage: any;
  base64Data: any;
  retrieveResponse: any;
  message: string;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // console.log(this.groupCode);
    // this.isFileSelected = false;
  }

  // Gets called when the user selects an image
  public onFileChanged(event) {
    // Select File
    this.selectedFile = event.target.files[0];
    // this.isFileSelected = true;
  }

  // Gets called when the user clicks on submit to upload the image
  onUpload() {
    // console.log(this.selectedFile);

    // FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    // const uploadGroupImage = new UploadGroupImage();
    // uploadGroupImage.uploadImageData = uploadImageData;
    // uploadGroupImage.groupCode = this.groupCode;

    // console.log(uploadGroupImage);
    // Make a call to the backend to save the image
    this.http.post(`http://localhost:8080/api/groups/details/${this.groupCode}/uploadImage`, uploadImageData, {observe: 'response'})
      .subscribe(response => {
        if (response.status === 200) {
          this.message = 'Image uploaded successfully';
          this.imageUploaded.emit(true);
        } else {
          this.message = 'Image not uploaded successfully';
        }
      });
  }

  // Gets called when the user clicks on retrieve image button to get the image from
  getImage() {
    // Make a call to backedn to get the Image Bytes
    this.http.get('http://localhost:8080/api/groups/details/${groupCode}/uploadImage' + this.imageName)
      .subscribe(
        res => {
          this.retrieveResponse = res;
          this.base64Data = this.retrieveResponse.picByte;
          this.retrievedImage = 'data:' + this.retrieveResponse.type + ';base64,' + this.base64Data;
        }
      );
  }

}

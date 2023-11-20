import { Component, ElementRef, Input, ViewChild, ViewEncapsulation } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';
import { bsAnimations } from '@ng-mf/shared/bs';
import { FileVO } from '@ng-mf/shared/service/core';
@Component({
    selector: 'ng-mf-bs-fileuploader',
    templateUrl: './file-uploader.component.html',
    styleUrls: ['./file-uploader.component.scss'],
    encapsulation: ViewEncapsulation.None,
    animations: bsAnimations
})

export class FileUploaderComponent {

    @Input()
    public frmGroup!: UntypedFormGroup;
    @ViewChild('avatarFileInput')
    private _avatarFileInput!: ElementRef;

    @Input()
    public obj!: any;

    @Input()
    public atribute!: string;

    nombre = null;
    avatar = null;
    /**
      * Upload avatar
      *
      * @param fileList
      */
    uploadAvatar(fileList?: FileList): void {
        // Return if canceled
        if (!fileList?.length) {
            return;
        }

        const allowedTypes = ['image/jpeg', 'image/png'];
        const file = fileList[0];

        // Return if the file is not allowed
        if (!allowedTypes.includes(file.type)) {
            return;
        }

        // Upload the avatar
        // this._contactsService.uploadAvatar(this.contact.id, file).subscribe();
    }

    /**
    * Remove the avatar
    */
    removeAvatar(): void {
        // Get the form control for 'avatar'
        const avatarFormControl = this.frmGroup.get(this.atribute);

        // Set the avatar as null
        avatarFormControl?.setValue(null);

        // Set the file input value as null
        this._avatarFileInput.nativeElement.value = null;

        // Update the contact
        this.obj[this.atribute] = null;
    }

}
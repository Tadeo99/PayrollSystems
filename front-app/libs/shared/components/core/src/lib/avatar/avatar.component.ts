import { Overlay } from '@angular/cdk/overlay';
import { ChangeDetectorRef, Component, ElementRef, Input, ViewChild, ViewContainerRef, ViewEncapsulation } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
@Component({
    selector: 'ng-mf-bs-avatar',
    templateUrl: './avatar.component.html',
    styleUrls: ['./avatar.component.scss'],
})
export class BsAvatarComponent {

    @Input()
    public frmGroup?: UntypedFormGroup;

    @ViewChild('avatarFileInput')
    private _avatarFileInput?: ElementRef;

    @Input()
    public atribute?: string;

    @Input()
    public name?: string;

    @Input()
    public avatar?: string | undefined | null;

    @Input()
    public id = 'avatar-file-input';

    

    /**
     * Constructor
     */
    constructor(
        private _activatedRoute: ActivatedRoute,
        private _changeDetectorRef: ChangeDetectorRef,
        private _overlay: Overlay,
        private _viewContainerRef: ViewContainerRef
    ) {
    }
    /**
      * Upload avatar
      *
      * @param fileList
      */
    uploadAvatar(fileList?: FileList | undefined | null): void {
        // Return if canceled
        if (!fileList) {
            return;
        }
        if (!fileList?.length) {
            return;
        }

        const allowedTypes = ['image/jpeg', 'image/png'];
        const file = fileList[0];

        // Return if the file is not allowed
        if (!allowedTypes.includes(file.type)) {
            return;
        }
        this._readAsDataURL(file).then((data) => {
            this.avatar = data;
            this.frmGroup?.get(this.atribute ?? '')?.setValue(data);
            this._changeDetectorRef.markForCheck();
        });
        // Upload the avatar
        // this._contactsService.uploadAvatar(this.contact.id, file).subscribe();
    }

    /**
     * Read the given file for demonstration purposes
     *
     * @param file
     */
    private _readAsDataURL(file: File): Promise<any> {
        // Return a new promise
        return new Promise((resolve, reject) => {

            // Create a new reader
            const reader = new FileReader();

            // Resolve the promise on success
            reader.onload = (): void => {
                resolve(reader.result);
            };

            // Reject the promise on error
            reader.onerror = (e): void => {
                reject(e);
            };

            // Read the file as the
            reader.readAsDataURL(file);
        });
    }

    /**
    * Remove the avatar
    */
    removeAvatar(): void {
        this.avatar = null;
        // Get the form control for 'avatar'
        const avatarFormControl = this.frmGroup?.get(this.atribute ?? '');

        // Set the avatar as null
        avatarFormControl?.setValue(null);

        // Set the file input value as null
        if (this._avatarFileInput != null && this._avatarFileInput?.nativeElement) {
            this._avatarFileInput.nativeElement.value = null;
        }
        // Update the contact
        //this.obj[this.atribute] = null;
        this._changeDetectorRef.markForCheck();
    }

}
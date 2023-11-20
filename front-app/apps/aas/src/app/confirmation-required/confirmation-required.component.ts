import { Component, ViewEncapsulation } from '@angular/core';
import { bsAnimations } from '@ng-mf/shared/bs';

@Component({
    selector: 'ng-mf-bs-auth-confirmation-required',
    templateUrl: './confirmation-required.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: bsAnimations
})
export class AuthConfirmationRequiredComponent {
    /**
     * Constructor
     */
    constructor() {
        //
    }
}

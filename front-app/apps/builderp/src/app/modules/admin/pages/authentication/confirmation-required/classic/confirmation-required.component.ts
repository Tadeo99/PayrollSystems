import { Component, ViewEncapsulation } from '@angular/core';
import { bsAnimations } from '@bs/animations';

@Component({
    selector     : 'ng-mf-confirmation-required-classic',
    templateUrl  : './confirmation-required.component.html',
    encapsulation: ViewEncapsulation.None,
    animations   : bsAnimations
})
export class ConfirmationRequiredClassicComponent
{
    /**
     * Constructor
     */
    constructor()
    {
    }
}

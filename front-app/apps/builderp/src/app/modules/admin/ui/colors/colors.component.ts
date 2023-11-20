import { Component, ViewEncapsulation } from '@angular/core';
import { bsAnimations } from '../../../../../@bs/animations';

@Component({
    selector     : 'ng-mf-bs-colors',
    templateUrl  : './colors.component.html',
    animations   : bsAnimations,
    encapsulation: ViewEncapsulation.None
})
export class ColorsComponent
{
    /**
     * Constructor
     */
    constructor()
    {
    }
}

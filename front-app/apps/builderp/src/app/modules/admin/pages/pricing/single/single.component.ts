import { ChangeDetectionStrategy, Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector     : 'ng-mf-pricing-single',
    templateUrl    : './single.component.html',
    encapsulation  : ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PricingSingleComponent
{
    /**
     * Constructor
     */
    constructor()
    {
    }
}

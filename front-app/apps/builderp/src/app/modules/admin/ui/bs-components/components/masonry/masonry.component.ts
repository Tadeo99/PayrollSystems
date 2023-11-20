import { Component, OnInit } from '@angular/core';
import { BsComponentsComponent } from 'app/modules/admin/ui/bs-components/bs-components.component';
import { Subject, takeUntil } from 'rxjs';
import { BsMediaWatcherService } from '@bs/services/media-watcher';

@Component({
    selector   : 'ng-mf-bs-masonry',
    templateUrl: './masonry.component.html'
})
export class MasonryComponent implements OnInit
{
    columns: number = 4;
    private _unsubscribeAll: Subject<any> = new Subject<any>();

    /**
     * Constructor
     */
    constructor(
        private _bsComponentsComponent: BsComponentsComponent,
        private _bsMediaWatcherService: BsMediaWatcherService
    )
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void
    {
        // Subscribe to media changes
        this._bsMediaWatcherService.onMediaChange$
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(({matchingAliases}) => {

                // Set the masonry columns
                //
                // This if block structured in a way so that only the
                // biggest matching alias will be used to set the column
                // count.
                if ( matchingAliases.includes('xl') )
                {
                    this.columns = 5;
                }
                else if ( matchingAliases.includes('lg') )
                {
                    this.columns = 4;
                }
                else if ( matchingAliases.includes('md') )
                {
                    this.columns = 3;
                }
                else if ( matchingAliases.includes('sm') )
                {
                    this.columns = 2;
                }
                else
                {
                    this.columns = 1;
                }
            });
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Toggle the drawer
     */
    toggleDrawer(): void
    {
        // Toggle the drawer
        this._bsComponentsComponent.matDrawer.toggle();
    }
}

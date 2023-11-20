import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { finalize } from 'rxjs';
import { BsLoadingService } from '@bs/services/loading';
import { BsComponentsComponent } from 'app/modules/admin/ui/bs-components/bs-components.component';

@Component({
    selector   : 'ng-mf-bs-loading-bar',
    templateUrl: './loading-bar.component.html'
})
export class LoadingBarComponent
{
    apiCallStatus = '-';
    mode: 'determinate' | 'indeterminate' = 'indeterminate';
    sliderValue = 0;

    /**
     * Constructor
     */
    constructor(
        private _httpClient: HttpClient,
        private _bsComponentsComponent: BsComponentsComponent,
        private _bsLoadingService: BsLoadingService
    )
    {
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

    /**
     * Show the loading bar
     */
    showLoadingBar(): void
    {
        this._bsLoadingService.show();
    }

    /**
     * Hide the loading bar
     */
    hideLoadingBar(): void
    {
        this._bsLoadingService.hide();
    }

    /**
     * Set the auto mode
     *
     * @param change
     */
    setAutoMode(change: MatSlideToggleChange): void
    {
        this._bsLoadingService.setAutoMode(change.checked);
    }

    /**
     * Make a fake API call
     */
    makeAPICall(): void
    {
        this.apiCallStatus = 'Waiting...';

        this._httpClient.get('https://jsonplaceholder.typicode.com/posts?_delay=2000')
            .pipe(finalize(() => {
                this.apiCallStatus = 'Finished!';
            }))
            .subscribe((response) => {
                console.log(response);
            });
    }

    /**
     * Toggle the mode
     */
    toggleMode(): void
    {
        // Show the loading bar
        this._bsLoadingService.show();

        // Set the mode
        this.mode = this.mode === 'indeterminate' ? 'determinate' : 'indeterminate';
        this._bsLoadingService.setMode(this.mode);
    }

    /**
     * Set the progress
     */
    setProgress(): void
    {
        this._bsLoadingService.setProgress(this.sliderValue);
    }
}

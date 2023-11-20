import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { bsAnimations } from '@ng-mf/shared/bs';
import { AuthService, UserService } from '@ng-mf/shared/service/aas';
import { BsAlertType } from '@ng-mf/shared/components/core';

@Component({
    selector: 'ng-mf-bs-auth-unlock-session',
    templateUrl: './unlock-session.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: bsAnimations
})
export class AuthUnlockSessionComponent implements OnInit {
    @ViewChild('unlockSessionNgForm') unlockSessionNgForm!: NgForm;

    alert: { type: BsAlertType; message: string } = {
        type: 'success',
        message: ''
    };
    name!: string;
    showAlert = false;
    unlockSessionForm!: UntypedFormGroup;
    private _email!: string;

    /**
     * Constructor
     */
    constructor(
        private _activatedRoute: ActivatedRoute,
        private _authService: AuthService,
        private _formBuilder: UntypedFormBuilder,
        private _router: Router,
        private _userService: UserService
    ) {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void {
        // Get the user's name
        this._userService.user$.subscribe((user) => {
            this.name = user.name;
            this._email = user.email;
        });

        // Create the form
        this.unlockSessionForm = this._formBuilder.group({
            name: [
                {
                    value: this.name,
                    disabled: true
                }
            ],
            password: ['', Validators.required]
        });
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Unlock
     */
    unlock(): void {
        // Return if the form is invalid
        if (this.unlockSessionForm.invalid) {
            return;
        }

        // Disable the form
        this.unlockSessionForm.disable();

        // Hide the alert
        this.showAlert = false;

        this._authService.unlockSession({
            email: this._email ?? '',
            password: this.unlockSessionForm.get('password')?.value
        }).subscribe(
            () => {

                // Set the redirect url.
                // The '/signed-in-redirect' is a dummy url to catch the request and redirect the user
                // to the correct page after a successful sign in. This way, that url can be set via
                // routing file and we don't have to touch here.
                const redirectURL = this._activatedRoute.snapshot.queryParamMap.get('redirectURL') || '/signed-in-redirect';

                // Navigate to the redirect url
                this._router.navigateByUrl(redirectURL);

            },
            (response) => {

                // Re-enable the form
                this.unlockSessionForm.enable();

                // Reset the form
                this.unlockSessionNgForm.resetForm({
                    name: {
                        value: this.name,
                        disabled: true
                    }
                });

                // Set the alert
                this.alert = {
                    type: 'error',
                    message: 'Invalid password'
                };

                // Show the alert
                this.showAlert = true;
            }
        );
    }
}

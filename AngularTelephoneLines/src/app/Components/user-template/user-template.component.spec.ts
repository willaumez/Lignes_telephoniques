import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTemplateComponent } from './user-template.component';

describe('UserTemplateComponent', () => {
  let component: UserTemplateComponent;
  let fixture: ComponentFixture<UserTemplateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserTemplateComponent]
    });
    fixture = TestBed.createComponent(UserTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

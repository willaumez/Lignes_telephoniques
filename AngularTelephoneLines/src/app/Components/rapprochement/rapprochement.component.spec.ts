import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { RapprochementComponent } from './rapprochement.component';

describe('RapprochementComponent', () => {
  let component: RapprochementComponent;
  let fixture: ComponentFixture<RapprochementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [RapprochementComponent]
    });
    fixture = TestBed.createComponent(RapprochementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
